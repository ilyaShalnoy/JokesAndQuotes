package com.example.notes.jokeapp.data.net

import com.example.notes.jokeapp.core.Mapper
import com.example.notes.jokeapp.data.CommonDataModel
import com.google.gson.annotations.SerializedName

class QuoteServerModel(
    @SerializedName("_id")
    private val id: String,
    @SerializedName("content")
    private val content: String,
    @SerializedName("author")
    private val author: String
) : Mapper<CommonDataModel<String>> {
    override fun to(): CommonDataModel<String> {
        return CommonDataModel(id, content, author)
    }
}