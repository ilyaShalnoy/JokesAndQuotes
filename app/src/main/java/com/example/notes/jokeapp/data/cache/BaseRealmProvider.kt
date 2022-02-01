package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.data.cache.RealmProvider
import io.realm.Realm

class BaseRealmProvider : RealmProvider {
    override fun provide(): Realm = Realm.getDefaultInstance()
}