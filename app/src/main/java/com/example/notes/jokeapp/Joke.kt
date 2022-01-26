package com.example.notes.jokeapp

class Joke(
    private val id: Int,
    private val category: String,
    private val type: String,
    private val text: String,
    private val punchline: String
) {
    fun toBaseJoke() = BaseJokeUiModel(text, punchline)
    fun toFavoriteJoke() = FavoriteJokeUiModel(text, punchline)
    fun change(cacheDataSource: CacheDataSource): JokeUiModel = cacheDataSource.addOrRemove(id, this)
    fun toRealmJoke(): JokeRealm {
        return JokeRealm().also {
            it.id = id
            it.category = category
            it.type = type
            it.text = text
            it.punchline = punchline
        }
    }
}