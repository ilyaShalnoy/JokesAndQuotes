package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.ChangeStatus
import com.example.notes.jokeapp.core.data.cache.CachedData
import com.example.notes.jokeapp.core.data.ChangeCommonItem
import com.example.notes.jokeapp.data.CommonDataModel

class BaseCachedData<E> : CachedData<E> {

    private var cached: ChangeCommonItem<E> = ChangeCommonItem.Empty()

    override fun save(item: CommonDataModel<E>) {
        cached = item
    }

    override fun clear() {
        cached = ChangeCommonItem.Empty()
    }

    override suspend fun change(changeStatus: ChangeStatus<E>): CommonDataModel<E> {
        return cached.change(changeStatus)
    }
}