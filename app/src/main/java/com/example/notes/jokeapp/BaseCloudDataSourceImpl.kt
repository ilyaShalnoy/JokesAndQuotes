package com.example.notes.jokeapp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

class BaseCloudDataSourceImpl(private val service: JokeService) : CloudDataSource {


    override suspend fun getJoke(): Result<JokeServerModel, ErrorType> {
        return try {
            val result = service.getJoke().execute().body()!!
            Log.d("threadLogTag", "Current thread: ${Thread.currentThread().name}")
            Result.Success(result)
        } catch (e: Exception) {
            var errorType = if (e is UnknownHostException)
                ErrorType.NO_CONNECTION
            else
                ErrorType.SERVICE_UNAVAILABLE
            Result.Error(errorType)
        }
    }
}