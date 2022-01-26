package com.example.notes.jokeapp

class TestModel(resourceManager: ResourceManager) : Model {

    private var callback: JokeCallback? = null

    private var count = 1

    private val noConnection = NoConnection(resourceManager)
    private val serviceUnavailable = ServiceUnavailable(resourceManager)

    override fun getJoke() {

        Thread {
            Thread.sleep(2000)
            when (count) {
                1 -> callback?.provideJoke(BaseJokeUiModel("testText", "testPunchline"))
                2 -> callback?.provideJoke(FavoriteJokeUiModel("favoriteText", "favouritePunchline"))
                3 -> callback?.provideJoke(FailedJokeUiModel(serviceUnavailable.getMessage()))
            }
            count++
        }.start()

    }

    override fun init(jokeCallback: JokeCallback) {
        this.callback = jokeCallback
    }

    override fun clear() {
        callback = null
    }

    override fun changeJokeStatus(callback: JokeCallback) {
        TODO("Not yet implemented")
    }

    override fun chooseDataSource(cached: Boolean) {
        TODO("Not yet implemented")
    }
}