package com.example.notes.jokeapp

interface CloudDataSource {
    fun getJoke(callback: JokeClaudCallback)
}