package com.example.notes.jokeapp

interface CachedJoke: ChangeJoke {

    fun saveJoke(joke: Joke)
    fun clear()

}