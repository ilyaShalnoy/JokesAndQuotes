package com.example.notes.jokeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudDataSource: CloudDataSource,
    private val resourcesManager: ResourceManager
) : Model {

    private var cachedJoke: Joke? = null
    private var getJokeFromCache = false

    private val noConnection by lazy { NoConnection(resourcesManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourcesManager) }
    private val noCachedJokes by lazy { NoCachedJokes(resourcesManager) }

    override suspend fun getJoke(): JokeUiModel = withContext(Dispatchers.IO) {
        if (getJokeFromCache) {
            return@withContext when (val result = cacheDataSource.getJoke()) {
                is Result.Success<Joke> -> {
                    cachedJoke = result.data
                    val joke = result.data.toFavoriteJoke()
                    joke
                }
                is Result.Error<Unit> -> {
                    cachedJoke = null
                    FailedJokeUiModel(noCachedJokes.getMessage())
                }
            }
        } else {
            return@withContext  when (val result = cloudDataSource.getJoke()) {
                is Result.Success<JokeServerModel> ->
                    result.data.toJoke().let {
                        cachedJoke = it
                        it.toBaseJoke()
                    }
                is Result.Error<ErrorType> -> {
                    cachedJoke = null
                    val failure = if (result.exception == ErrorType.NO_CONNECTION)
                        noConnection
                    else
                        serviceUnavailable
                    FailedJokeUiModel(failure.getMessage())
                }

            }
        }
    }

    override suspend fun changeJokeStatus(): JokeUiModel? {
       return cachedJoke?.change(cacheDataSource)
    }

    override fun chooseDataSource(cached: Boolean) {
        getJokeFromCache = cached
    }
}