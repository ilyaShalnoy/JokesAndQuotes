package com.example.notes.jokeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.jokeapp.JokesModule
import com.example.notes.jokeapp.QuoteModule
import java.lang.IllegalStateException

class ViewModelFactory(
    private val jokesModule: JokesModule,
    private val quotesModule: QuoteModule
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val module = when {
            modelClass.isAssignableFrom(JokesViewModel::class.java) -> jokesModule
            modelClass.isAssignableFrom(QuotesViewModel::class.java) -> quotesModule
            else -> throw IllegalStateException("unknown type of viewModel")
        }
        return module.getViewModules() as T
    }
}