package com.example.notes.jokeapp.data.mapper

import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.data.cache.QuoteRealmModel

class QuoteRealmMapper : CommonDataModelMapper<QuoteRealmModel, String> {
    override fun map(id: String, first: String, second: String, cached: Boolean): QuoteRealmModel {
        return QuoteRealmModel().also { quote ->
            quote.id = id
            quote.content = first
            quote.author = second
        }
    }
}