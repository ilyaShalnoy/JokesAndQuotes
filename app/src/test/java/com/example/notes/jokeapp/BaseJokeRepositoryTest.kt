package com.example.notes.jokeapp

import com.example.notes.jokeapp.core.ResourceManager
import com.example.notes.jokeapp.core.data.cache.CacheDataSource
import com.example.notes.jokeapp.core.data.net.CloudDataSource
import com.example.notes.jokeapp.data.cache.BaseCachedData
import com.example.notes.jokeapp.data.*
import com.example.notes.jokeapp.data.net.JokeServerModel
import com.example.notes.jokeapp.domain.CommonItem
import com.example.notes.jokeapp.presentation.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseJokeRepositoryTest {

    @Test
    fun test_change_data_source(): Unit = runBlocking {
        val cachedDataSource = TestCacheDataSource()
        val cloudDataSource = TestCloudDataSource()
        val cachedJokes = BaseCachedData()

        val cloudResultHandler = CloudResultHandler(
            cachedJokes,
            cloudDataSource,
            NoConnection(TestResourceManager()),
            ServiceUnavailable(TestResourceManager())
        )

        val cacheResultHandler = CacheResultHandler(
            cachedJokes,
            cachedDataSource,
            NoCachedData(TestResourceManager())
        )
        val model = BaseRepository(cachedDataSource, cloudResultHandler, cacheResultHandler, cachedJokes)

        val jokeCloudFailure = model.getCommonItem()
        assertEquals(jokeCloudFailure is FailedCommonUiModel, true)
        cloudDataSource.getJokeWithResult(false, 1)
        assertEquals(jokeCloudFailure is FailedCommonUiModel, true)

        cloudDataSource.getJokeWithResult(true)
        val jokeCloud = model.getCommonItem()
        assertEquals(jokeCloud is BaseCommonUiModel, true)
        model.changeStatus()
        assertEquals(cachedDataSource.checkContainsId(0), true)

        cachedDataSource.getNextJokeWithResult(true, 0)
        model.chooseDataSource(true)
        val jokeCache = model.getCommonItem()
        assertEquals(jokeCache is FavoriteCommonUiModel, true)

        cachedDataSource.getNextJokeWithResult(false, 0)
        val jokeCacheFailure = model.getCommonItem()
        assertEquals(jokeCacheFailure is FailedCommonUiModel, true)
    }

    private inner class TestCacheDataSource : CacheDataSource {

        private val map = HashMap<Int, CommonItem>()
        private var success: Boolean = false
        private var nextJokeIdToGet = 0

        fun getNextJokeWithResult(success: Boolean, id: Int) {
            this.success = success
            nextJokeIdToGet = id
        }

        override suspend fun getData(): com.example.notes.jokeapp.data.Result<CommonItem, Unit> {
            return if (success) {
                com.example.notes.jokeapp.data.Result.Success(map[0]!!)
            } else {
                com.example.notes.jokeapp.data.Result.Error(Unit)
            }
        }

        override suspend fun addOrRemove(id: Int, joke: CommonDataModel): CommonUiModel {
            return if (map.containsKey(id)) {
                val result = map[id]!!.toBaseJoke()
                map.remove(id)
                result
            } else {
                map[id] = joke
                joke.toFavoriteJoke()
            }
        }

        fun checkContainsId(id: Int) = map.containsKey(id)
    }

    private inner class TestCloudDataSource : CloudDataSource {

        private var success = false
        private var count = 0
        private var error = 0

        override suspend fun getData(): com.example.notes.jokeapp.data.Result<JokeServerModel, ErrorType> {
            return if (success) {
                com.example.notes.jokeapp.data.Result.Success(JokeServerModel(count++, "category", "type", "text $count", "puncline$count"))
            } else {
                val errorType = if (error == 0) ErrorType.NO_CONNECTION else ErrorType.SERVICE_UNAVAILABLE
                com.example.notes.jokeapp.data.Result.Error(errorType)
            }
        }

        fun getJokeWithResult(success: Boolean, error: Int = 0) {
            this.success = success
            this.error = error
        }
    }

    private inner class TestResourceManager : ResourceManager {

        val message: String = ""

        override fun getString(stringResId: Int): String {
            return message
        }
    }
}

