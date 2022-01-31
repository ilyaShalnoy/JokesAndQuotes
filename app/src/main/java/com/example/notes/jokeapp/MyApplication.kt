package com.example.notes.jokeapp

import android.app.Application
import com.example.notes.jokeapp.data.*
import com.example.notes.jokeapp.domain.BaseJokeInteractor
import com.example.notes.jokeapp.domain.JokeFailureFactory
import com.example.notes.jokeapp.presentation.*
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(JokeService::class.java)

        val cachedJoke: CachedJoke = BaseCachedJoke()
        val cacheDataSource = BaseCachedDataSource(BaseRealmProvider(), JokeRealmMapper())
        val resourceManager = BaseResourcesManager(this)
        val cloudDataSource = BaseCloudDataSource(service)
        val repository = BaseJokeRepository(cacheDataSource, cloudDataSource, cachedJoke)
        val interactor = BaseJokeInteractor(repository, JokeFailureFactory(resourceManager), JokeSuccessMapper())

        viewModel = MainViewModel(interactor, BaseCommunication())

    }
}