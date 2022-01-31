package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.presentation.JokeUiModel

interface JokeRepository {

    suspend fun getJoke(): JokeDataModel

    suspend fun changeJokeStatus(): JokeDataModel

    fun chooseDataSource(cached: Boolean)
}

