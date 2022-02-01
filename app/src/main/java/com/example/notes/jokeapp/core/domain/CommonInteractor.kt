package com.example.notes.jokeapp.core.domain

import com.example.notes.jokeapp.domain.CommonItem

interface CommonInteractor {

    suspend fun getItem(): CommonItem
    suspend fun changeFavorites(): CommonItem
    fun chooseFavorites(favorites: Boolean)

}