package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.domain.Joke

class JokeDataModel(
    private val id: Int,
    private val category: String,
    private val type: String,
    private val text: String,
    private val punchline: String,
    private val cached: Boolean = false
) : ChangeJoke {

    fun <T> map(mapper: JokeDataModelMapper<T>): T {
        return mapper.map(id, category, type, text, punchline, cached)
    }

    override suspend fun change(changeJokeStatus: ChangeJokeStatus): JokeDataModel =
        changeJokeStatus.addOrRemove(id, this)

    fun changeCached(cached: Boolean): JokeDataModel =
        JokeDataModel(id, category, type, text, punchline, cached)


}