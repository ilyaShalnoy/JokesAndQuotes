package com.example.notes.jokeapp.domain

import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.data.ResourceManager
import com.example.notes.jokeapp.presentation.*
import java.lang.Exception

interface JokeFailureHandler {
    fun handle(e: Exception): JokeFailure
}


class JokeFailureFactory(private val resourceManager: ResourceManager) : JokeFailureHandler {

    override fun handle(e: Exception): JokeFailure {
        return when (e) {
            is NoConnectionException -> NoConnection(resourceManager)
            is ServiceUnavailableException -> ServiceUnavailable(resourceManager)
            is NoCachedJokesException -> NoCachedJokes(resourceManager)
            else -> GenericError(resourceManager)
        }
    }
}