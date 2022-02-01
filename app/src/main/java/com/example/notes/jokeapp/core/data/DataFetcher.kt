package com.example.notes.jokeapp.core.data

import com.example.notes.jokeapp.data.CommonDataModel

interface DataFetcher<E> {
    suspend fun getData(): CommonDataModel<E>
}