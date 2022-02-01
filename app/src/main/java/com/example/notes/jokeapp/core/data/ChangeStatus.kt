package com.example.notes.jokeapp.core.data

import com.example.notes.jokeapp.data.CommonDataModel

interface ChangeStatus<E> {

    suspend fun addOrRemove(id: E, model: CommonDataModel<E>): CommonDataModel<E>
}