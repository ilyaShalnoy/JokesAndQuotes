package com.example.notes.jokeapp.core.domain

import com.example.notes.jokeapp.core.presentation.Failure

interface FailureHandler {
    fun handle(e: Exception): Failure
}


