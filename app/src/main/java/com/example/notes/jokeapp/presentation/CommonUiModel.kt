package com.example.notes.jokeapp.presentation

import androidx.annotation.DrawableRes
import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.core.presentation.Communication


class BaseCommonUiModel(private val text: String, private val punchline: String) : CommonUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_empty
}

class FavoriteCommonUiModel(private val text: String, private val punchline: String) : CommonUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_fill
}

class FailedCommonUiModel(private val text: String) : CommonUiModel(text, "") {
    override fun text() = text
    override fun getIconResId() = 0
    override fun show(communication: Communication) = communication.showState(
        BaseViewModel.State.Failed(text(), getIconResId())
    )
}

abstract class CommonUiModel(private val firstText: String, private val secondText: String) {

    protected open fun text() = "$firstText\n$secondText"

    @DrawableRes
    protected abstract fun getIconResId(): Int

    open fun show(communication: Communication) = communication.showState(
        BaseViewModel.State.Initial(text(), getIconResId())
    )
}
