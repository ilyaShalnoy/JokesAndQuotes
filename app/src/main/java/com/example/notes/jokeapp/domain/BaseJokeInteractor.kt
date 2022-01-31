package com.example.notes.jokeapp.domain

import androidx.annotation.StringRes
import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.data.BaseResourcesManager
import com.example.notes.jokeapp.data.JokeDataModelMapper
import com.example.notes.jokeapp.data.JokeRepository
import com.example.notes.jokeapp.data.ResourceManager
import com.example.notes.jokeapp.presentation.NoCachedJokes
import com.example.notes.jokeapp.presentation.NoConnection
import com.example.notes.jokeapp.presentation.ServiceUnavailable
import java.lang.Exception

class BaseJokeInteractor(
    private val jokeRepository: JokeRepository,
    private val jokeFailureHandler: JokeFailureHandler,
    private val mapper: JokeDataModelMapper<Joke.Success>
) : JokeInteractor {


    override suspend fun getJoke(): Joke {
        return try {
            jokeRepository.getJoke().map(mapper)
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override suspend fun changeFavorites(): Joke {
        return try {
            jokeRepository.changeJokeStatus().map(mapper)
        } catch (e: Exception) {
            Joke.Failed(jokeFailureHandler.handle(e))
        }
    }

    override fun chooseFavoriteJoke(favorites: Boolean) {
        jokeRepository.chooseDataSource(favorites)
    }
}

