package com.example.notes.jokeapp

interface CacheDataSource {

    fun addOrRemove(id: Int, joke: Joke): JokeUiModel
    fun getJoke(jokeCachedCallback: JokeCachedCallback)
}

interface JokeCachedCallback {

    fun provide(joke: Joke)

    fun fail()

}