package com.example.notes.jokeapp

interface Model {

    suspend fun getJoke(): JokeUiModel

    suspend fun changeJokeStatus(): JokeUiModel?

    fun chooseDataSource(cached: Boolean)
}

