package com.example.notes.jokeapp

class BaseCachedJoke : CachedJoke {

    private var cached: Joke? = null

    override fun saveJoke(joke: Joke) {
        cached = joke
    }

    override fun clear() {
        cached = null
    }

    override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeUiModel? {
        return cached?.change(changeJokeStatus)
    }
}