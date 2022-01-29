package com.example.notes.jokeapp

import android.app.Application
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        val cachedJoke: CachedJoke = BaseCachedJoke()
        val cacheDataSource = BaseCachedDataSource(BaseRealmProvider())
        val resourceManager = BaseResourcesManager(this)

        Realm.init(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(JokeService::class.java)

        viewModel = MainViewModel(
            BaseModel(
                cacheDataSource,
                CloudResultHandler(
                    cachedJoke,
                    BaseCloudDataSource(service),
                    NoConnection(resourceManager),
                    ServiceUnavailable(resourceManager)
                ),
                CacheResultHandler(
                    cachedJoke,
                    cacheDataSource,
                    NoCachedJokes(resourceManager)
                ), cachedJoke
            ),
            BaseCommunication()
        )
    }

}