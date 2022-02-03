package com.example.notes.jokeapp

import com.example.notes.jokeapp.core.data.cache.RealmProvider
import com.example.notes.jokeapp.core.domain.FailureHandler
import com.example.notes.jokeapp.data.CommonSuccessMapper
import com.example.notes.jokeapp.data.cache.BaseCachedData
import com.example.notes.jokeapp.data.cache.QuoteCachedDataSource
import com.example.notes.jokeapp.data.cache.QuoteRealmToCommonMapper
import com.example.notes.jokeapp.data.mapper.QuoteRealmMapper
import com.example.notes.jokeapp.data.net.QuoteCloudDataSource
import com.example.notes.jokeapp.data.net.QuoteService
import com.example.notes.jokeapp.domain.BaseInteractor
import com.example.notes.jokeapp.presentation.BaseCommunication
import com.example.notes.jokeapp.presentation.QuotesViewModel
import retrofit2.Retrofit

class QuoteModule(
    private val failureHandler: FailureHandler,
    private val realmProvider: RealmProvider,
    private val retrofit: Retrofit
) : BaseModule<String, QuotesViewModel>() {

    private var communication: BaseCommunication<String>? = null

    override fun getViewModules(): QuotesViewModel = QuotesViewModel(getInteractor(), getCommunication())

    override fun getCommunication(): BaseCommunication<String> {
        if (communication == null)
            communication = BaseCommunication()
        return communication!!
    }

    private fun getInteractor() = BaseInteractor(getRepository(), failureHandler, CommonSuccessMapper())

    private fun getRepository() = BaseRepository(getCacheDataSource(), getCloudDataSource(), BaseCachedData())

    private fun getCacheDataSource() = QuoteCachedDataSource(realmProvider, QuoteRealmMapper(), QuoteRealmToCommonMapper())

    private fun getCloudDataSource() = QuoteCloudDataSource(retrofit.create(QuoteService::class.java))
}