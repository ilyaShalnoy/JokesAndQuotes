package com.example.notes.jokeapp.data.net

import retrofit2.Call

class JokeCloudDataSource(private val service: JokeService) :
    BaseCloudDataSource<JokeServerModel, Int>() {
    override fun getServerModel(): Call<JokeServerModel> = service.getJoke()
}