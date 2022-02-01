package com.example.notes.jokeapp.core.data.cache

import com.example.notes.jokeapp.core.data.ChangeStatus
import com.example.notes.jokeapp.core.data.DataFetcher

interface CacheDataSource<E>: DataFetcher<E>, ChangeStatus<E> {
}