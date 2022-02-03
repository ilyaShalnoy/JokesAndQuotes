package com.example.notes.jokeapp

import com.example.notes.jokeapp.presentation.BaseCommunication
import com.example.notes.jokeapp.presentation.BaseViewModel

abstract class BaseModule<E, T : BaseViewModel<E>> {
    abstract fun getViewModules(): T
    abstract fun getCommunication(): BaseCommunication<E>
}