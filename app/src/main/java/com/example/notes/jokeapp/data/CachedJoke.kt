package com.example.notes.jokeapp.data

interface CachedJoke: ChangeJoke {

    fun saveJoke(joke: JokeDataModel)
    fun clear()

}