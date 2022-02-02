package com.example.notes.jokeapp.data

import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.domain.CommonItem

class CommonSuccessMapper<E> : CommonDataModelMapper<CommonItem.Success<E>, E> {
    override fun map(id: E, first: String, second: String, cached: Boolean) =
        CommonItem.Success(id, first, second, cached)
}