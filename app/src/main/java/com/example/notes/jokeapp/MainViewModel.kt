package com.example.notes.jokeapp

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val model: Model,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {


    fun getJoke() = viewModelScope.launch(dispatcher) {
        communication.showState(State.Progress)
        model.getJoke().show(communication)
    }

    fun changeJokeStatus() = viewModelScope.launch(dispatcher) {
        model.changeJokeStatus()?.let {
            it.show(communication)
        }
    }

    fun chooseFavorite(favorite: Boolean) {
        model.chooseDataSource(favorite)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        communication.observe(owner, observer)
    }


    sealed class State {

        fun show(progress: ShowView, button: EnableView, textView: ShowText, imageButton: ShowImage) {
            show(progress, button)
            show(textView, imageButton)
        }

        protected open fun show(progress: ShowView, button: EnableView) {}
        protected open fun show(textView: ShowText, imageButton: ShowImage) {}


        object Progress : State() {
            override fun show(progress: ShowView, button: EnableView) {
                progress.show(true)
                button.enable(false)
            }
        }

        class Initial(val text: String, @DrawableRes val id: Int) : State() {

            override fun show(progress: ShowView, button: EnableView) {
                progress.show(false)
                button.enable(true)
            }

            override fun show(textView: ShowText, imageButton: ShowImage) {
                textView.show(text)
                imageButton.show(id)
            }
        }
    }
}




