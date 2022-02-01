package com.example.notes.jokeapp.data.net

import retrofit2.Call

class QuoteCloudDataSource(private val service: QuoteService) :
    BaseCloudDataSource<QuoteServerModel, String>() {
    override fun getServerModel(): Call<QuoteServerModel> = service.getQuote()
}