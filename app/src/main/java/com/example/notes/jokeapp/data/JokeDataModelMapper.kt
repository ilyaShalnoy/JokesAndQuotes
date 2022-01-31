package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.domain.Joke

interface JokeDataModelMapper<T> {
    fun map(id: Int, category: String, type: String, text: String, punchline: String, cached: Boolean): T

}

class JokeSuccessMapper : JokeDataModelMapper<Joke.Success> {
    override fun map(id: Int, category: String, type: String, text: String, punchline: String, cached: Boolean) =
        Joke.Success(text, punchline, cached)
}

class JokeRealmMapper : JokeDataModelMapper<JokeRealm> {
    override fun map(id: Int, category: String, type: String, text: String, punchline: String, cached: Boolean): JokeRealm {
        return JokeRealm().also { joke ->
            joke.id = id
            joke.text = text
            joke.punchline = punchline
        }
    }

}