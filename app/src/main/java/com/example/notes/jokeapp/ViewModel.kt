package com.example.notes.jokeapp

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModel(private val model: Model): ViewModel() {

    private var dataCallback: DataCallback? = null

    fun getJoke() = viewModelScope.launch{
        val uiModel = model.getJoke()
        dataCallback?.let {
            uiModel.map(it)
        }
    }

    fun init(callback: DataCallback) {
        this.dataCallback = callback

    }

    fun clear() {
        dataCallback = null
    }

    fun changeJokeStatus() = viewModelScope.launch {
        val uiModel = model.changeJokeStatus()
        dataCallback?.let {
            uiModel?.map(it)
        }
    }

    fun chooseFavorite(favorite: Boolean) {
        model.chooseDataSource(favorite)
    }

}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id: Int)

}
