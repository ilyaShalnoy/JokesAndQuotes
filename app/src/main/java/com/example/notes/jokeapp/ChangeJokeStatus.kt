package com.example.notes.jokeapp

interface ChangeJokeStatus {

    suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel
}