package com.example.notes.jokeapp.core.data.cache

import com.example.notes.jokeapp.core.data.ChangeStatus
import com.example.notes.jokeapp.core.data.DataFetcher
import com.example.notes.jokeapp.data.CommonDataModel

interface CacheDataSource<E>: DataFetcher<E>, ChangeStatus<E> {
    suspend fun getDataList(): List<CommonDataModel<E>>
    suspend fun remove(id: E)
}