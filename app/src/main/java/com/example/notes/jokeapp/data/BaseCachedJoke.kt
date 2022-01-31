package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.domain.Joke
import com.example.notes.jokeapp.presentation.JokeUiModel

class BaseCachedJoke : CachedJoke {

    private var cached: ChangeJoke = ChangeJoke.Empty()

    override fun saveJoke(joke: JokeDataModel) {
        cached = joke
    }

    override fun clear() {
        cached = ChangeJoke.Empty()
    }

    override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel {
        return cached.change(changeJokeStatus)
    }
}