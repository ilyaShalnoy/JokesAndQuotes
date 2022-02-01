package com.example.notes.jokeapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.core.data.CommonRepository
import com.example.notes.jokeapp.core.presentation.Communication
import com.example.notes.jokeapp.presentation.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class BaseMainViewJokeRepositoryTest {

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_joke_from_claud_success(): Unit = runBlocking {

        val model = TestJokeRepository()
        val communication = TestCommunication()
        val viewModel = BaseViewModel(model, communication, UnconfinedTestDispatcher())

        model.success = true
        viewModel.chooseFavorite(false)
        viewModel.getJoke()

        val actualText = communication.text
        val actualId = communication.id
        val expectedText = "cloudJokeText\ncloudJokePunchline"
        assertEquals(expectedText, actualText)
        assertNotEquals(0, actualId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_joke_from_claud_fail(): Unit = runBlocking {

        val model = TestJokeRepository()
        val communication = TestCommunication()
        val viewModel = BaseViewModel(model, communication, UnconfinedTestDispatcher())

        model.success = false
        viewModel.chooseFavorite(false)
        viewModel.getJoke()

        val actualText = communication.text
        val actualId = communication.id
        val expectedText = "no connection"
        val expectedId = 0
        assertEquals(expectedText, actualText)
        assertEquals(expectedId, actualId)
    }

    private inner class TestJokeRepository : CommonRepository {

        private val cacheJokeUiModel = BaseCommonUiModel("cachedJokeText", "cachedJokePunchline")
        private val cacheJokeFailure = FailedCommonUiModel("cacheFailed")
        private val cloudJokeUiModel = BaseCommonUiModel("cloudJokeText", "cloudJokePunchline")
        private val cloudJokeFailure = FailedCommonUiModel("no connection")
        var success: Boolean = false
        var cachedJoke: CommonUiModel? = null
        private var getFromCache = false

        override suspend fun getCommonItem(): CommonUiModel {
            return if (success) {
                if (getFromCache) {
                    cacheJokeUiModel.also {
                        cachedJoke = it
                    }
                } else {
                    cloudJokeUiModel.also {
                        cachedJoke = it
                    }
                }
            } else {
                cachedJoke = null
                if (getFromCache) {
                    cacheJokeFailure
                } else {
                    cloudJokeFailure
                }
            }
        }

        override suspend fun changeStatus(): CommonUiModel? {
            return null
        }

        override fun chooseDataSource(cached: Boolean) {
            getFromCache = cached
        }
    }

    private inner class TestCommunication : Communication {

        var text = ""
        var id = -1
        var observedCount = 0

        override fun showData(data: Pair<String, Int>) {
            text = data.first
            id = data.second
        }

        override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<Pair<String, Int>>) {
            observedCount++
        }
    }
}

