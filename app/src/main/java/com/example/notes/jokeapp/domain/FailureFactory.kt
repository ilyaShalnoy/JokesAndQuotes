package com.example.notes.jokeapp.domain

import com.example.notes.jokeapp.core.domain.FailureHandler
import com.example.notes.jokeapp.core.domain.NoCachedException
import com.example.notes.jokeapp.core.domain.NoConnectionException
import com.example.notes.jokeapp.core.domain.ServiceUnavailableException
import com.example.notes.jokeapp.core.presentation.Failure
import com.example.notes.jokeapp.core.ResourceManager
import com.example.notes.jokeapp.presentation.*
import java.lang.Exception

class FailureFactory(private val resourceManager: ResourceManager) : FailureHandler {

    override fun handle(e: Exception): Failure {
        return when (e) {
            is NoConnectionException -> NoConnection(resourceManager)
            is ServiceUnavailableException -> ServiceUnavailable(resourceManager)
            is NoCachedException -> NoCachedData(resourceManager)
            else -> GenericError(resourceManager)
        }
    }
}