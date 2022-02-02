package com.example.notes.jokeapp

import android.app.Application
import com.example.notes.jokeapp.core.presentation.CommonCommunication
import com.example.notes.jokeapp.data.*
import com.example.notes.jokeapp.data.cache.*
import com.example.notes.jokeapp.data.mapper.*
import com.example.notes.jokeapp.data.net.JokeCloudDataSource
import com.example.notes.jokeapp.data.net.JokeService
import com.example.notes.jokeapp.data.net.QuoteCloudDataSource
import com.example.notes.jokeapp.data.net.QuoteService
import com.example.notes.jokeapp.domain.BaseInteractor
import com.example.notes.jokeapp.domain.FailureFactory
import com.example.notes.jokeapp.presentation.*
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    lateinit var viewModel: BaseViewModel<Int>
    lateinit var quoteViewModel: BaseViewModel<String>
    lateinit var jokeCommunication: CommonCommunication<Int>

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val resourceManager = BaseResourcesManager(this)
        val realmProvider = BaseRealmProvider()
        val jokeRepository = BaseRepository(
            JokeCachedDataSource(realmProvider, JokeRealmMapper(), JokeRealmToCommonMapper()),
            JokeCloudDataSource(retrofit.create(JokeService::class.java)),
            BaseCachedData()
        )
        val quoteRepository = BaseRepository(
            QuoteCachedDataSource(realmProvider, QuoteRealmMapper(), QuoteRealmToCommonMapper()),
            QuoteCloudDataSource(retrofit.create(QuoteService::class.java)),
            BaseCachedData()
        )

        val failureFactory = FailureFactory(resourceManager)
        val quoteMapper = CommonSuccessMapper<String>()
        val jokeMapper = CommonSuccessMapper<Int>()

        jokeCommunication = BaseCommunication()

        BaseViewModel(BaseInteractor(jokeRepository, failureFactory, jokeMapper), jokeCommunication).also { viewModel = it }

        BaseViewModel(BaseInteractor(quoteRepository, failureFactory, quoteMapper), BaseCommunication()).also {
            quoteViewModel = it
        }

    }
}