package com.example.notes.jokeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudResultHandler: CloudResultHandler,
    private val cacheResultHandler: CacheResultHandler,
    private val cachedJoke: CachedJoke
) : Model {

    private var currentResultHandler: BaseResultHandler<*, *> = cloudResultHandler

    override fun chooseDataSource(cached: Boolean) {
        currentResultHandler = if (cached) cacheResultHandler else cloudResultHandler
    }

    override suspend fun getJoke(): JokeUiModel = withContext(Dispatchers.IO) {
        return@withContext currentResultHandler.process()
    }

    override suspend fun changeJokeStatus(): JokeUiModel? {
        return cachedJoke.change(cacheDataSource)
    }

}

abstract class BaseResultHandler<S, E>(
    private val jokeDataFetcher: JokeDataFetcher<S, E>
) {

    suspend fun process(): JokeUiModel {
        return handleResult(jokeDataFetcher.getJoke())
    }

    protected abstract fun handleResult(result: Result<S, E>): JokeUiModel
}

class CloudResultHandler(
    private val cachedJoke: CachedJoke,
    jokeDataFetcher: JokeDataFetcher<JokeServerModel, ErrorType>,
    private val noConnection: JokeFailure,
    private val serviceUnavailable: ServiceUnavailable
) : BaseResultHandler<JokeServerModel, ErrorType>(jokeDataFetcher) {

    override fun handleResult(result: Result<JokeServerModel, ErrorType>) = when (result) {
        is Result.Success<JokeServerModel> ->
            result.data.toJoke().let {
                cachedJoke.saveJoke(it)
                it.toBaseJoke()
            }
        is Result.Error<ErrorType> -> {
            cachedJoke.clear()
            val failure = if (result.exception == ErrorType.NO_CONNECTION)
                noConnection
            else
                serviceUnavailable
            FailedJokeUiModel(failure.getMessage())
        }
    }
}

class CacheResultHandler(
    private val cachedJoke: CachedJoke,
    jokeDataFetcher: JokeDataFetcher<Joke, Unit>,
    private val noCachedJokes: NoCachedJokes
) : BaseResultHandler<Joke, Unit>(jokeDataFetcher) {

    override fun handleResult(result: Result<Joke, Unit>) = when (result) {
        is Result.Success<Joke> -> result.data.let {
            cachedJoke.saveJoke(it)
            it.toFavoriteJoke()
        }
        is Result.Error<Unit> -> {
            cachedJoke.clear()
            FailedJokeUiModel(noCachedJokes.getMessage())
        }
    }
}