package com.example.notes.jokeapp.data.cache

import com.example.notes.jokeapp.core.Mapper
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class JokeRealm : RealmObject() {
    @PrimaryKey
    var id: Int = -1
    var category: String = ""
    var type: String = ""
    var text: String = ""
    var punchline: String = ""

}