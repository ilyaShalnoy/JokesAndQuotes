package com.example.notes.jokeapp.data

interface JokeDataFetcher {
    suspend fun getJoke(): JokeDataModel
}