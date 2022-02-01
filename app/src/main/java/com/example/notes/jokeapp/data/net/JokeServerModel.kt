package com.example.notes.jokeapp.data.net

import com.example.notes.jokeapp.core.Mapper
import com.example.notes.jokeapp.data.CommonDataModel
import com.google.gson.annotations.SerializedName

data class JokeServerModel(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("category")
    private val category: String,
    @SerializedName("type")
    private val type: String,
    @SerializedName("setup")
    private val text: String,
    @SerializedName("delivery")
    private val punchline: String
) : Mapper<CommonDataModel<Int>> {

    override fun to(): CommonDataModel<Int> = CommonDataModel(id, text, punchline)
}