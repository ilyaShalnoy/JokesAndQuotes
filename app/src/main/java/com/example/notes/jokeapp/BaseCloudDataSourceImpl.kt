package com.example.notes.jokeapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class BaseCloudDataSourceImpl(private val service: JokeService) : CloudDataSource {


    override fun getJoke(callback: JokeClaudCallback) {
        service.getJoke().enqueue(object : Callback<JokeServerModel> {

            override fun onResponse(call: Call<JokeServerModel>, response: Response<JokeServerModel>) {
                if (response.isSuccessful)
                    callback.provide(response.body()!!.toJoke())
                else
                    callback.fail(ErrorType.SERVICE_UNAVAILABLE)
            }

            override fun onFailure(call: Call<JokeServerModel>, t: Throwable) {
                var errorType = if (t is UnknownHostException)
                    ErrorType.NO_CONNECTION
                else
                    ErrorType.SERVICE_UNAVAILABLE
                callback.fail(errorType)
            }
        })
    }
}