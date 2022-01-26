package com.example.notes.jokeapp

import io.realm.Realm

class BaseCachedDataSource(private val realm: Realm) : CacheDataSource {

    override fun addOrRemove(id: Int, joke: Joke): JokeUiModel {
        realm.let {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            return if (jokeRealm == null) {
                val newJoke = joke.toRealmJoke()
                it.executeTransactionAsync { transaction ->
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

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        realm.use {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty()) {
                jokeCachedCallback.fail()
            } else
                jokes.random().let { joke ->
                    jokeCachedCallback.provide(
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