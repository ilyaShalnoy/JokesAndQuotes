package com.example.notes.jokeapp.core.data

interface CommonDataModelMapper<T, E> {
    fun map(id: E, first: String, second: String, cached: Boolean): T

}

