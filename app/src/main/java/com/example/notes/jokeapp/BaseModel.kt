package com.example.notes.jokeapp

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudDataSource: CloudDataSource,
    private val resourcesManager: ResourceManager
) : Model {

    private var jokeCallback: JokeCallback? = null
    private var cachedJoke: Joke? = null
    private var getJokeFromCache = false

    private val noConnection by lazy { NoConnection(resourcesManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourcesManager) }
    private val noCachedJokes by lazy { NoCachedJokes(resourcesManager) }

    override fun getJoke() {
        if (getJokeFromCache) {
            cacheDataSource.getJoke(object : JokeCachedCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provideJoke(joke.toFavoriteJoke())
                }

                override fun fail() {
                    cachedJoke = null
                    jokeCallback?.provideJoke(FailedJokeUiModel(noCachedJokes.getMessage()))
                }
            })
        } else {
            cloudDataSource.getJoke(object : JokeClaudCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provideJoke(joke.toBaseJoke())
                }

                override fun fail(error: ErrorType) {
                    cachedJoke = null
                    val failure = if (error == ErrorType.NO_CONNECTION) noConnection else serviceUnavailable
                    jokeCallback?.provideJoke(FailedJokeUiModel(failure.getMessage()))
                }
            })
        }
    }

    override fun init(jokeCallback: JokeCallback) {
        this.jokeCallback = jokeCallback
    }

    override fun clear() {
        jokeCallback = null
    }

    override fun changeJokeStatus(callback: JokeCallback) {
        cachedJoke?.change(cacheDataSource)?.let {
            callback.provideJoke(it)
        }
    }

    override fun chooseDataSource(cached: Boolean) {
        getJokeFromCache = cached
    }
}