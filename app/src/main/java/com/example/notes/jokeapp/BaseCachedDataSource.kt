package com.example.notes.jokeapp

import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseCachedDataSource(private val realmProvider: RealmProvider) : CacheDataSource {

    override suspend fun addOrRemove(id: Int, joke: Joke): JokeUiModel =
        withContext(Dispatchers.IO) {
            Realm.getDefaultInstance().use {
                val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
                return@withContext if (jokeRealm == null) {
                    val newJoke = joke.toRealmJoke()
                    it.executeTransaction { transaction ->
                        transaction.insert(newJoke)
                    }
                    joke.toFavoriteJoke()
                } else {
                    it.executeTransaction {
                        jokeRealm.deleteFromRealm()
                    }
                    joke.toBaseJoke()
                }
            }
        }


    override suspend fun getJoke(): Result<Joke, Unit> {
        realmProvider.provide().let {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                return Result.Error(Unit)
            } else
                jokes.random().let { joke ->
                    return@getJoke Result.Success(
                        Joke(
                            joke.id,
                            joke.category,
                            joke.type,
                            joke.text,
                            joke.punchline
                        )
                    )
                }
        }
    }


}