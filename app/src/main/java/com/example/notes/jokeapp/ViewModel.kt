package com.example.notes.jokeapp

import androidx.annotation.DrawableRes

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null

    private val jokeCallback = object : JokeCallback {
        override fun provideJoke(jokeUiModel: JokeUiModel) {
            dataCallback?.let {
                jokeUiModel.map(it)
            }
        }
    }

    fun getJoke() {
        model.getJoke()
    }

    fun init(callback: DataCallback) {
        this.dataCallback = callback
        model.init(jokeCallback)
    }

    fun clear() {
        dataCallback = null
        model.clear()
    }

    fun changeJokeStatus() {
        model.changeJokeStatus(jokeCallback)
    }

    fun chooseFavorite(favorite: Boolean) {
        model.chooseDataSource(favorite)
    }

}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id: Int)

}
