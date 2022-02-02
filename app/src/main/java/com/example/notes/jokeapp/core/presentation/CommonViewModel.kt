package com.example.notes.jokeapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.presentation.BaseViewModel
import com.example.notes.jokeapp.presentation.CommonUiModel

interface CommonViewModel<T> : CommonItemViewModel {
    fun changeItemStatus(id: T, owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>): Int
    fun observe(owner: LifecycleOwner, observer: Observer<BaseViewModel.State>)
    fun observeList(owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>)

}


interface CommonItemViewModel {
    fun getItem()
    fun getItemList()
    fun changeItemStatus()
    fun chooseFavorites(favorites: Boolean)
}