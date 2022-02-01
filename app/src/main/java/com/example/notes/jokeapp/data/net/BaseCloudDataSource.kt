package com.example.notes.jokeapp.data.net

import com.example.notes.jokeapp.core.Mapper
import com.example.notes.jokeapp.core.data.net.CloudDataSource
import com.example.notes.jokeapp.core.domain.NoConnectionException
import com.example.notes.jokeapp.core.domain.ServiceUnavailableException
import com.example.notes.jokeapp.data.CommonDataModel
import retrofit2.Call
import java.net.UnknownHostException

abstract class BaseCloudDataSource<T : Mapper<CommonDataModel<E>>, E> : CloudDataSource<E> {

    protected abstract fun getServerModel(): Call<T>

    override suspend fun getData(): CommonDataModel<E> {
        try {
            return getServerModel().execute().body()!!.to()
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                throw NoConnectionException()
            } else {
                throw ServiceUnavailableException()
            }
        }
    }
}

