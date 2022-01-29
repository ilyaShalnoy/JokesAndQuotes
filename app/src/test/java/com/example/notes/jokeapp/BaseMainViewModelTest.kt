package com.example.notes.jokeapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class BaseMainViewModelTest {

    @ExperimentalCoroutinesApi
    @Test
    fun test_get_joke_from_claud_success(): Unit = runBlocking {

        val model = TestModel()
        val communication = TestCommunication()
        val viewModel = MainViewModel(model, communication, UnconfinedTestDispatcher())

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

        val model = TestModel()
        val communication = TestCommunication()
        val viewModel = MainViewModel(model, communication, UnconfinedTestDispatcher())

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

    private inner class TestModel : Model {

        private val cacheJokeUiModel = BaseJokeUiModel("cachedJokeText", "cachedJokePunchline")
        private val cacheJokeFailure = FailedJokeUiModel("cacheFailed")
        private val cloudJokeUiModel = BaseJokeUiModel("cloudJokeText", "cloudJokePunchline")
        private val cloudJokeFailure = FailedJokeUiModel("no connection")
        var success: Boolean = false
        var cachedJoke: JokeUiModel? = null
        private var getFromCache = false

        override suspend fun getJoke(): JokeUiModel {
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

        override suspend fun changeJokeStatus(): JokeUiModel? {
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

