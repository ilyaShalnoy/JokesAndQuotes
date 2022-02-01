package com.example.notes.jokeapp.core.data

import com.example.notes.jokeapp.data.CommonDataModel

interface CommonRepository<E> {

    suspend fun getCommonItem(): CommonDataModel<E>

    suspend fun changeStatus(): CommonDataModel<E>

    fun chooseDataSource(cached: Boolean)
}

