package com.example.notes.jokeapp

import android.app.Application
import com.example.notes.jokeapp.core.data.cache.RealmProvider
import com.example.notes.jokeapp.core.domain.FailureHandler
import com.example.notes.jokeapp.data.cache.BaseRealmProvider
import com.example.notes.jokeapp.domain.FailureFactory
import com.example.notes.jokeapp.presentation.BaseResourcesManager
import com.example.notes.jokeapp.presentation.ViewModelFactory
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    val viewModelFactory by lazy {
        ViewModelFactory(
            JokesModule(failureHandler, realmProvider, retrofit),
            QuoteModule(failureHandler, realmProvider, retrofit)
        )
    }

    private lateinit var retrofit: Retrofit
    private lateinit var failureHandler: FailureHandler
    private lateinit var realmProvider: RealmProvider

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val resourceManager = BaseResourcesManager(this)

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        realmProvider = BaseRealmProvider()
        failureHandler = FailureFactory(resourceManager)


    }
}