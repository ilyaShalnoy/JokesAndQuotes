package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.cache.RealmProvider
import com.example.notes.jokeapp.data.mapper.QuoteRealmMapper
import io.realm.Realm

class QuoteCachedDataSource(
    realmProvider: RealmProvider,
    mapper: QuoteRealmMapper,
    commonDataMapper: QuoteRealmToCommonMapper
) :
    BaseCachedDataSource<QuoteRealmModel, String>(realmProvider, mapper, commonDataMapper) {

    override val dbClass: Class<QuoteRealmModel> = QuoteRealmModel::class.java

    override fun findRealmObject(realm: Realm, id: String): QuoteRealmModel? {
        return realm.where(dbClass).equalTo("id", id).findFirst()
    }
}