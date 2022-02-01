package com.example.notes.jokeapp.core.data.cache

import com.example.notes.jokeapp.data.CommonDataModel
import io.realm.RealmObject

interface RealmToCommonDataMapper<T : RealmObject, E> {

    fun map(realmObject: T): CommonDataModel<E>
}

