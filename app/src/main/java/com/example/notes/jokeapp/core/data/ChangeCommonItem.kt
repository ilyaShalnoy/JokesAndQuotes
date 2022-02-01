package com.example.notes.jokeapp.core.data

import com.example.notes.jokeapp.data.CommonDataModel

interface ChangeCommonItem<E> {

    suspend fun change(changeStatus: ChangeStatus<E>): CommonDataModel<E>

    class Empty<E> : ChangeCommonItem<E> {
        override suspend fun change(changeStatus: ChangeStatus<E>): CommonDataModel<E> {
            throw IllegalStateException("empty change joke called")
        }
    }
}

