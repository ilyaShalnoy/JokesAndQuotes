package com.example.notes.jokeapp.core.domain

import com.example.notes.jokeapp.domain.CommonItem

interface CommonInteractor<T> {

    suspend fun getItem(): CommonItem<T>
    suspend fun getItemList(): List<CommonItem<T>>
    suspend fun changeFavorites(): CommonItem<T>
    fun chooseFavorites(favorites: Boolean)
    suspend fun removeItem(id: T)

}