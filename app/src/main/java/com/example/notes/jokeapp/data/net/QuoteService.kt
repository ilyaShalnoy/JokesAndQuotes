package com.example.notes.jokeapp.data.net

import retrofit2.Call
import retrofit2.http.GET

interface QuoteService {

    @GET("https://api.quotable.io/random")
    fun getQuote(): Call<QuoteServerModel>

}