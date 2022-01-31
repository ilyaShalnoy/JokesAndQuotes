package com.example.notes.jokeapp.data

import android.util.Log
import com.example.notes.jokeapp.domain.NoConnectionException
import com.example.notes.jokeapp.domain.ServiceUnavailableException
import java.lang.Exception
import java.net.UnknownHostException

class BaseCloudDataSource(private val service: JokeService) : CloudDataSource {


    override suspend fun getJoke(): JokeDataModel {
        return try {
            service.getJoke().execute().body()!!.to()
        } catch (e: Exception) {
            if (e is UnknownHostException)
                throw NoConnectionException()
            else
                throw ServiceUnavailableException()
        }
    }
}