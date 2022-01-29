package com.example.notes.jokeapp

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseModelTest {

    @Test
    fun test_change_data_source(): Unit = runBlocking {
        val cachedDataSource = TestCacheDataSource()
        val cloudDataSource = TestCloudDataSource()
        val cachedJokes = BaseCachedJoke()

        val cloudResultHandler = CloudResultHandler(
            cachedJokes,
            cloudDataSource,
            NoConnection(TestResourceManager()),
            ServiceUnavailable(TestResourceManager())
        )

        val cacheResultHandler = CacheResultHandler(
            cachedJokes,
            cachedDataSource,
            NoCachedJokes(TestResourceManager())
        )
        val model = BaseModel(cachedDataSource, cloudResultHandler, cacheResultHandler, cachedJokes)

        val jokeCloudFailure = model.getJoke()
        assertEquals(jokeCloudFailure is FailedJokeUiModel, true)
        cloudDataSource.getJokeWithResult(false, 1)
        assertEquals(jokeCloudFailure is FailedJokeUiModel, true)

        cloudDataSource.getJokeWithResult(true)
        val jokeCloud = model.getJoke()
        assertEquals(jokeCloud is BaseJokeUiModel, true)
        model.changeJokeStatus()
        assertEquals(cachedDataSource.checkContainsId(0), true)

        cachedDataSource.getNextJokeWithResult(true, 0)
        model.chooseDataSource(true)
        val jokeCache = model.getJoke()
        assertEquals(jokeCache is FavoriteJokeUiModel, true)

        cachedDataSource.getNextJokeWithResult(false, 0)
        val jokeCacheFailure = model.getJoke()
        assertEquals(jokeCacheFailure is FailedJokeUiModel, true)
    }

    private inner class TestCacheDataSource : CacheDataSource {

        private val map = HashMap<Int, Joke>()
        private var success: Boolean = false
        private var nextJokeIdToGet = 0

        fun getNextJokeWithResult(success: Boolean, id: Int) {
            this.success = success
            nextJokeIdToGet = id
        }

        override suspend fun getJoke(): Result<Joke, Unit> {
            return if (success) {
                Result.Success(map[0]!!)
            } else {
                Result.Error(Unit)
            }
        }

        override suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel {
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

        override suspend fun getJoke(): Result<JokeServerModel, ErrorType> {
            return if (success) {
                Result.Success(JokeServerModel(count++, "category", "type", "text $count", "puncline$count"))
            } else {
                val errorType = if (error == 0) ErrorType.NO_CONNECTION else ErrorType.SERVICE_UNAVAILABLE
                Result.Error(errorType)
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

