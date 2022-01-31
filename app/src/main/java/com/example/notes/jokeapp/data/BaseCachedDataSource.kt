package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.*
import com.example.notes.jokeapp.domain.Joke
import com.example.notes.jokeapp.domain.NoCachedJokesException
import com.example.notes.jokeapp.presentation.JokeUiModel
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseCachedDataSource(
    private val realmProvider: RealmProvider,
    private val mapper: JokeDataModelMapper<JokeRealm>
    ) : CacheDataSource {

    override suspend fun addOrRemove(id: Int, joke: JokeDataModel): JokeDataModel =
        withContext(Dispatchers.IO) {
            realmProvider.provide().use {
                val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
                return@withContext if (jokeRealm == null) {
                    val newJoke = joke.map(mapper)
                    it.executeTransaction { transaction ->
                        transaction.insert(newJoke)
                    }
                    joke.changeCached(true)
                } else {
                    it.executeTransaction {
                        jokeRealm.deleteFromRealm()
                    }
                    joke.changeCached(false)
                }
            }
        }


    override suspend fun getJoke(): JokeDataModel {
        realmProvider.provide().use {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                throw NoCachedJokesException()
            } else
                return jokes.random().to()
        }
    }
}