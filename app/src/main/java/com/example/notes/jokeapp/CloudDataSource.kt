package com.example.notes.jokeapp

interface CloudDataSource {

    suspend fun getJoke(): Result<JokeServerModel, ErrorType>
}