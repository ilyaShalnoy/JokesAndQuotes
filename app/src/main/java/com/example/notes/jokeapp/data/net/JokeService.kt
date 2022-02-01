package com.example.notes.jokeapp.data.net

import com.example.notes.jokeapp.data.net.JokeServerModel
import retrofit2.Call
import retrofit2.http.GET

interface JokeService {

    @GET("https://v2.jokeapi.dev/joke/Misc?type=twopart")
    fun getJoke(): Call<JokeServerModel>

}



