package com.example.notes.jokeapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication {

    fun showState(state: MainViewModel.State);

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<MainViewModel.State>);

    fun isState(type: Int): Boolean

}

class BaseCommunication : Communication {

    private val liveData = MutableLiveData<MainViewModel.State>()

    override fun showState(state: MainViewModel.State) {
        liveData.value = state
    }

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<MainViewModel.State>) {
        liveData.observe(lifecycleOwner, observer)
    }

    override fun isState(type: Int): Boolean {
        return liveData.value?.isType(type) ?: false
    }
}