package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.core.data.ChangeStatus
import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.core.data.ChangeCommonItem

class CommonDataModel<E>(
    private val id: E,
    private val text: String,
    private val punchline: String,
    private val cached: Boolean = false
) : ChangeCommonItem<E> {

    fun <T> map(mapper: CommonDataModelMapper<T, E>): T {
        return mapper.map(id, text, punchline, cached)
    }

    override suspend fun change(changeStatus: ChangeStatus<E>): CommonDataModel<E> =
        changeStatus.addOrRemove(id, this)

    fun changeCached(cached: Boolean): CommonDataModel<E> =
        CommonDataModel(id, text, punchline, cached)


}