package com.example.notes.jokeapp.domain

import com.example.notes.jokeapp.core.Mapper
import com.example.notes.jokeapp.presentation.*

sealed class Joke() : Mapper<JokeUiModel> {
    class Success(
        private val text: String,
        private val punchline: String,
        private val favorite: Boolean
    ) : Joke() {

        override fun to(): JokeUiModel {
            return if (favorite) {
                FavoriteJokeUiModel(text, punchline)
            } else {
                BaseJokeUiModel(text, punchline)
            }
        }
    }

    class Failed(private val failure: JokeFailure) : Joke() {
        override fun to(): JokeUiModel {
            return FailedJokeUiModel(failure.getMessage())
        }
    }
}