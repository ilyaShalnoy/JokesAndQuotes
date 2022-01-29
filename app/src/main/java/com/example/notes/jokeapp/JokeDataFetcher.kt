package com.example.notes.jokeapp

interface JokeDataFetcher<S,E> {
    suspend fun getJoke(): Result<S, E>
}