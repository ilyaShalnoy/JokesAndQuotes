package com.example.notes.jokeapp.data

interface ChangeJoke {

    suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel

    class Empty : ChangeJoke {
        override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel {
            throw IllegalStateException("empty change joke called")
        }
    }
}

