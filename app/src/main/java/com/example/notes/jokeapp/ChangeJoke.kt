package com.example.notes.jokeapp

interface ChangeJoke {

    suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeUiModel?

}