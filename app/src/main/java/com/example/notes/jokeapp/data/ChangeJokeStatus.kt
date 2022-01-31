package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.domain.Joke
import com.example.notes.jokeapp.presentation.JokeUiModel

interface ChangeJokeStatus {

    suspend fun addOrRemove(id: Int, joke: JokeDataModel): JokeDataModel
}