package com.example.notes.jokeapp

import android.app.Application
import com.google.gson.Gson
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    lateinit var viewModel: ViewModel
    lateinit var resourceManager: ResourceManager

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(JokeService::class.java)

        resourceManager = BaseResourcesManager(this)
        viewModel = ViewModel(BaseModel(BaseCachedDataSource(BaseRealmProvider()), BaseCloudDataSourceImpl(service), resourceManager))
    }

}