package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.cache.RealmToCommonDataMapper
import com.example.notes.jokeapp.data.CommonDataModel
import com.example.notes.jokeapp.data.cache.QuoteRealmModel

class QuoteRealmToCommonMapper : RealmToCommonDataMapper<QuoteRealmModel, String> {
    override fun map(realmObject: QuoteRealmModel): CommonDataModel<String> =
        CommonDataModel(realmObject.id, realmObject.content, realmObject.author, true)


}