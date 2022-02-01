package com.example.notes.jokeapp.data.cache

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QuoteRealmModel : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var content: String = ""
    var author: String = ""

}