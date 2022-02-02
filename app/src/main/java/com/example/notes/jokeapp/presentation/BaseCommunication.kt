package com.example.notes.jokeapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.example.notes.jokeapp.core.presentation.CommonCommunication

class BaseCommunication<T> : CommonCommunication<T> {

    private val liveData = MutableLiveData<BaseViewModel.State>()
    private val listLiveData = MutableLiveData<ArrayList<CommonUiModel<T>>>()

    private lateinit var diffResult: DiffUtil.DiffResult

    override fun getDiffResult() = diffResult

    override fun showState(state: BaseViewModel.State) {
        liveData.value = state
    }

    override fun showDataList(list: List<CommonUiModel<T>>) {
        val callback = CommonDiffUtilCallback(getList(), list)
        diffResult = DiffUtil.calculateDiff(callback)
        listLiveData.value = ArrayList(list)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<CommonUiModel<T>>>) {
        listLiveData.observe(owner, observer)
    }

    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewModel.State>) {
        liveData.observe(lifecycleOwner, observer)
    }

    override fun isState(type: Int): Boolean {
        return liveData.value?.isType(type) ?: false
    }

    override fun getList(): List<CommonUiModel<T>> {
        return listLiveData.value ?: emptyList()
    }


}