package com.example.notes.jokeapp

import com.example.notes.jokeapp.core.data.cache.RealmProvider
import com.example.notes.jokeapp.core.domain.FailureHandler
import com.example.notes.jokeapp.data.CommonSuccessMapper
import com.example.notes.jokeapp.data.cache.BaseCachedData
import com.example.notes.jokeapp.data.cache.JokeCachedDataSource
import com.example.notes.jokeapp.data.cache.JokeRealmToCommonMapper
import com.example.notes.jokeapp.data.mapper.JokeRealmMapper
import com.example.notes.jokeapp.data.net.JokeCloudDataSource
import com.example.notes.jokeapp.data.net.JokeService
import com.example.notes.jokeapp.domain.BaseInteractor
import com.example.notes.jokeapp.presentation.BaseCommunication
import com.example.notes.jokeapp.presentation.JokesViewModel
import retrofit2.Retrofit

class JokesModule(
    private val failureHandler: FailureHandler,
    private val realmProvider: RealmProvider,
    private val retrofit: Retrofit
) : BaseModule<Int, JokesViewModel>() {

    private var communication: BaseCommunication<Int>? = null

    override fun getViewModules(): JokesViewModel = JokesViewModel(getInteractor(), getCommunication())

    override fun getCommunication(): BaseCommunication<Int> {
        if (communication == null)
            communication = BaseCommunication()
        return communication!!
    }

    private fun getInteractor() = BaseInteractor(getRepository(), failureHandler, CommonSuccessMapper())

    private fun getRepository() = BaseRepository(getCacheDataSource(), getCloudDataSource(), BaseCachedData())

    private fun getCacheDataSource() = JokeCachedDataSource(realmProvider, JokeRealmMapper(), JokeRealmToCommonMapper())

    private fun getCloudDataSource() = JokeCloudDataSource(retrofit.create(JokeService::class.java))
}