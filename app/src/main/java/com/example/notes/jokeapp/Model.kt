package com.example.notes.jokeapp

interface Model {

    fun getJoke()

    fun init(jokeCallback: JokeCallback)

    fun clear()

    fun changeJokeStatus(callback: JokeCallback)

    fun chooseDataSource(cached: Boolean)
}

interface JokeCallback {

    fun provideJoke(jokeUiModel: JokeUiModel)

}