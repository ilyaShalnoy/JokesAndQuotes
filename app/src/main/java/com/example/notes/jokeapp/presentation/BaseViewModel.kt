package com.example.notes.jokeapp.presentation

import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.notes.jokeapp.core.domain.CommonInteractor
import com.example.notes.jokeapp.core.presentation.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseViewModel(
    private val interactor: CommonInteractor,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), CommonViewModel {


    override fun getItem() {
        viewModelScope.launch(dispatcher) {
            communication.showState(State.Progress)
            interactor.getItem().to().show(communication)
        }
    }

    override fun changeItemStatus() {
        viewModelScope.launch(dispatcher) {
            if (communication.isState(State.INITIAL)) {
                interactor.changeFavorites().to().show(communication)
            }
        }
    }

    override fun chooseFavorites(favorites: Boolean) = interactor.chooseFavorites(favorites)


    override fun observe(owner: LifecycleOwner, observer: Observer<State>) = communication.observe(owner, observer)


    sealed class State {

        protected abstract val type: Int

        companion object {
            const val INITIAL = 0
            const val PROGRESS = 1
            const val FAILED = 2
        }

        fun isType(type: Int): Boolean = this.type == type

        fun show(progress: ShowView, button: EnableView, textView: ShowText, imageButton: ShowImage) {
            show(progress, button)
            show(textView, imageButton)
        }

        protected open fun show(progress: ShowView, button: EnableView) {}
        protected open fun show(textView: ShowText, imageButton: ShowImage) {}


        object Progress : State() {
            override val type: Int = PROGRESS

            override fun show(progress: ShowView, button: EnableView) {
                progress.show(true)
                button.enable(false)
            }
        }

        abstract class Info(private val text: String, @DrawableRes private val id: Int) : State() {
            override fun show(progress: ShowView, button: EnableView) {
                progress.show(false)
                button.enable(true)
            }

            override fun show(textView: ShowText, imageButton: ShowImage) {
                textView.show(text)
                imageButton.show(id)
            }
        }

        class Initial(text: String, @DrawableRes private val id: Int) : Info(text, id) {
            override val type: Int = INITIAL
        }

        class Failed(text: String, @DrawableRes private val id: Int) : Info(text, id) {
            override val type: Int = FAILED

        }
    }
}




