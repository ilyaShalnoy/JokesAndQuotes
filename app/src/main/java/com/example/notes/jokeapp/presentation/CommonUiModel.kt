package com.example.notes.jokeapp.presentation

import androidx.annotation.DrawableRes
import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.core.presentation.CommonCommunication
import com.example.notes.jokeapp.core.presentation.Communication
import com.example.notes.jokeapp.core.presentation.ShowText


class BaseCommonUiModel<E>(private val text: String, private val punchline: String) : CommonUiModel<E>(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_empty
}

class FavoriteCommonUiModel<E>(private val id: E, text: String, punchline: String) : CommonUiModel<E>(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_fill
    override fun change(listener: CommonDataRecyclerAdapter.FavoriteItemClickListener<E>) = listener.change(id)

    override fun matches(id: E): Boolean = this.id == id

    override fun same(model: CommonUiModel<E>): Boolean {
        return (model is FavoriteCommonUiModel<E>) && (model.id == id)
    }
}

class FailedCommonUiModel<E>(private val text: String) : CommonUiModel<E>(text, "") {
    override fun text() = text
    override fun getIconResId() = 0
    override fun show(communication: Communication) = communication.showState(
        BaseViewModel.State.Failed(text(), getIconResId())
    )
}

abstract class CommonUiModel<T>(private val firstText: String, private val secondText: String) {

    protected open fun text() = "$firstText\n$secondText"

    open fun same(model: CommonUiModel<T>): Boolean = false

    @DrawableRes
    protected abstract fun getIconResId(): Int

    open fun show(communication: Communication) = communication.showState(
        BaseViewModel.State.Initial(text(), getIconResId())
    )

    open fun matches(id: T): Boolean = false

    open fun change(listener: CommonDataRecyclerAdapter.FavoriteItemClickListener<T>) = Unit

    fun show(showText: ShowText) = showText.show(text())
}
