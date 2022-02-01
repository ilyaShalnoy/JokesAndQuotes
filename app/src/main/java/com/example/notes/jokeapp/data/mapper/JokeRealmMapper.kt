package com.example.notes.jokeapp.data.mapper

import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.data.cache.JokeRealm

class JokeRealmMapper : CommonDataModelMapper<JokeRealm, Int> {
    override fun map(id: Int, first: String, second: String, cached: Boolean): JokeRealm {
        return JokeRealm().also { joke ->
            joke.id = id
            joke.text = first
            joke.punchline = second
        }
    }
}