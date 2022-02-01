package com.example.notes.jokeapp.core.data.cache

import com.example.notes.jokeapp.core.data.ChangeCommonItem
import com.example.notes.jokeapp.data.CommonDataModel

interface CachedData<E>: ChangeCommonItem<E> {

    fun save(item: CommonDataModel<E>)
    fun clear()

}