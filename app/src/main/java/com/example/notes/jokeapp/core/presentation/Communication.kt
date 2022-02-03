package com.example.notes.jokeapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.presentation.BaseViewModel
import com.example.notes.jokeapp.presentation.State

interface Communication {

    fun showState(state: State);

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<State>);

    fun isState(type: Int): Boolean
}