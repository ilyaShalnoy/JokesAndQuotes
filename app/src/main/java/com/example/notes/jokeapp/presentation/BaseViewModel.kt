package com.example.notes.jokeapp.presentation

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.notes.jokeapp.core.domain.CommonInteractor
import com.example.notes.jokeapp.core.presentation.*
import com.example.notes.jokeapp.domain.CommonItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    private val name: String,
    private val interactor: CommonInteractor<T>,
    val communication: CommonCommunication<T>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), CommonViewModel<T> {


    init {
        Log.d("ViewModelTag", "init $name")
    }

    override fun getItem() {
        viewModelScope.launch(dispatcher) {
            communication.showState(State.Progress)
            interactor.getItem().to().show(communication)
        }
    }

    override fun getItemList() {
        viewModelScope.launch(dispatcher) {
            showList()
        }
    }

    override fun changeItemStatus(id: T) {
        viewModelScope.launch(dispatcher) {
            interactor.removeItem(id)
            showList()
        }
    }

    override fun chooseFavorites(favorites: Boolean) = interactor.chooseFavorites(favorites)

    override fun changeItemStatus() {
        viewModelScope.launch(dispatcher) {
            if (communication.isState(State.INITIAL)) {
                interactor.changeFavorites().to().show(communication)
                showList()
            }
        }
    }

    private suspend fun showList() = communication.showDataList(interactor.getItemList().toUiList())


    override fun observe(owner: LifecycleOwner, observer: Observer<State>) = communication.observe(owner, observer)

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>) = communication.observeList(owner, observer)


    override fun onCleared() {
        Log.d("ViewModelTag", "clear $name")
    }


    fun <T> List<CommonItem<T>>.toUiList() = map {
        it.to()
    }
}

class JokesViewModel(
    interactor: CommonInteractor<Int>,
    communication: CommonCommunication<Int>,
) : BaseViewModel<Int>("JokesViewModel", interactor, communication)

class QuotesViewModel(
    interactor: CommonInteractor<String>,
    communication: CommonCommunication<String>
) : BaseViewModel<String>("QuotesViewModel", interactor, communication)



