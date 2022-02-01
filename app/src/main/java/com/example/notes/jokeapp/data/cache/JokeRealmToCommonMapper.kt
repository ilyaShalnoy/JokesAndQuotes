package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.cache.RealmToCommonDataMapper
import com.example.notes.jokeapp.data.CommonDataModel
import com.example.notes.jokeapp.data.cache.JokeRealm

class JokeRealmToCommonMapper : RealmToCommonDataMapper<JokeRealm, Int> {
    override fun map(realmObject: JokeRealm): CommonDataModel<Int> =
        CommonDataModel(realmObject.id, realmObject.text, realmObject.punchline, true)
}