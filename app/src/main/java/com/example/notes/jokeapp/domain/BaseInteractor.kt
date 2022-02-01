package com.example.notes.jokeapp.domain

import com.example.notes.jokeapp.core.data.CommonDataModelMapper
import com.example.notes.jokeapp.core.data.CommonRepository
import com.example.notes.jokeapp.core.domain.CommonInteractor
import com.example.notes.jokeapp.core.domain.FailureHandler
import java.lang.Exception

class BaseInteractor<E>(
    private val commonRepository: CommonRepository<E>,
    private val failureHandler: FailureHandler,
    private val mapper: CommonDataModelMapper<CommonItem.Success, E>
) : CommonInteractor {


    override suspend fun getItem(): CommonItem {
        return try {
            commonRepository.getCommonItem().map(mapper)
        } catch (e: Exception) {
            CommonItem.Failed(failureHandler.handle(e))
        }
    }

    override suspend fun changeFavorites(): CommonItem {
        return try {
            commonRepository.changeStatus().map(mapper)
        } catch (e: Exception) {
            CommonItem.Failed(failureHandler.handle(e))
        }
    }

    override fun chooseFavorites(favorites: Boolean) {
        commonRepository.chooseDataSource(favorites)
    }
}

