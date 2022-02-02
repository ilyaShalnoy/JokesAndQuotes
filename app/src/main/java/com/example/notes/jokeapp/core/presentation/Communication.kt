package com.example.notes.jokeapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.presentation.BaseViewModel

interface Communication {

    fun showState(state: BaseViewModel.State);

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewModel.State>);

    fun isState(type: Int): Boolean
}