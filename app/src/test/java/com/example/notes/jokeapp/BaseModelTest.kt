package com.example.notes.jokeapp

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class BaseModelTest {

    @Test
    fun test_change_data_source(): Unit = runBlocking {
        val cachedDataSource = TestCacheDataSource()
        val cloudDataSource = TestCloudDataSource()
        val model = BaseModel(cachedDataSource, cloudDataSource, TestResourceManager())
        model.chooseDataSource(false)
        cloudDataSource.getJokeWithResult(true)
        val joke = model.getJoke()
        assertEquals(joke is BaseJokeUiModel, true)
        model.changeJokeStatus()
        assertEquals(cachedDataSource.checkContainsId(0), true)
    }

    private inner class TestCacheDataSource : CacheDataSource {

        private val map = HashMap<Int, Joke>()
        private val success: Boolean = false
        private val nextJokeIdToGet = -1

        override suspend fun getJoke(): Result<Joke, Unit> {
            return if (success) {
                Result.Success(map[nextJokeIdToGet]!!)
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

        private var success = true
        private var count = 0

        override suspend fun getJoke(): Result<JokeServerModel, ErrorType> {
            return if (success) {
                Result.Success(JokeServerModel(count++, "category", "type", "text $count", "puncline$count"))
            } else
                Result.Error(ErrorType.NO_CONNECTION)
        }

        fun getJokeWithResult(success: Boolean) {
            this.success = success
        }
    }

    private inner class TestResourceManager : ResourceManager {

        val message: String = ""

        override fun getString(stringResId: Int): String {
            return message
        }
    }
}

