package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.cache.RealmProvider
import com.example.notes.jokeapp.data.mapper.JokeRealmMapper
import io.realm.Realm

class JokeCachedDataSource(
    realmProvider: RealmProvider,
    mapper: JokeRealmMapper,
    commonDataMapper: JokeRealmToCommonMapper
) :
    BaseCachedDataSource<JokeRealm, Int>(realmProvider, mapper, commonDataMapper) {

    override val dbClass: Class<JokeRealm> = JokeRealm::class.java
    override fun findRealmObject(realm: Realm, id: Int): JokeRealm? {
        return realm.where(dbClass).equalTo("id", id).findFirst()
    }
}