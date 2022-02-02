package com.example.notes.jokeapp.presentation

import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.notes.jokeapp.core.domain.CommonInteractor
import com.example.notes.jokeapp.core.presentation.*
import com.example.notes.jokeapp.domain.CommonItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseViewModel<T>(
    private val interactor: CommonInteractor<T>,
    private val communication: CommonCommunication<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), CommonViewModel<T> {


    override fun getItem() {
        viewModelScope.launch(dispatcher) {
            communication.showState(State.Progress)
            interactor.getItem().to().show(communication)
        }
    }

    override fun getItemList() {
        viewModelScope.launch(dispatcher) {
            communication.showDataList(interactor.getItemList().toUiList())
        }
    }

    override fun changeItemStatus(id: T, owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>): Int {
        val position = communication.removeItem(id, owner, observer)
        viewModelScope.launch(dispatcher) {
            interactor.removeItem(id)
        }
        return position
    }

    override fun chooseFavorites(favorites: Boolean) = interactor.chooseFavorites(favorites)

    override fun changeItemStatus() {
        viewModelScope.launch(dispatcher) {
            if (communication.isState(State.INITIAL)) {
                interactor.changeFavorites().to().show(communication)
                communication.showDataList(interactor.getItemList().toUiList())
            }
        }
    }


    override fun observe(owner: LifecycleOwner, observer: Observer<State>) = communication.observe(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>) = communication.observeList(owner, observer)

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

fun <T> List<CommonItem<T>>.toUiList() = map {
    it.to()
}



