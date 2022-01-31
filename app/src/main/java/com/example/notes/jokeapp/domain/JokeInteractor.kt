package com.example.notes.jokeapp.domain

interface JokeInteractor {

    suspend fun getJoke(): Joke
    suspend fun changeFavorites(): Joke
    fun chooseFavoriteJoke(favorites: Boolean)

}