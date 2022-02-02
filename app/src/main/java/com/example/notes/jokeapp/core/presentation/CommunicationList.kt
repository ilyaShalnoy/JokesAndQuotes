package com.example.notes.jokeapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.presentation.BaseViewModel
import com.example.notes.jokeapp.presentation.CommonUiModel

interface CommunicationList<T> {

    fun showDataList(list: List<CommonUiModel<T>>)

    fun observeList(owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>)

    fun removeItem(id: T, owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>): Int

    fun getList(): List<CommonUiModel<T>>

}