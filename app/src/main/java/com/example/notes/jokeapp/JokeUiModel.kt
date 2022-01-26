package com.example.notes.jokeapp

import androidx.annotation.DrawableRes


class BaseJokeUiModel(private val text: String, private val punchline: String) : JokeUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_empty
}

class FavoriteJokeUiModel(private val text: String, private val punchline: String) : JokeUiModel(text, punchline) {
    override fun getIconResId() = R.drawable.ic_favorite_fill
}

class FailedJokeUiModel(private val text: String) : JokeUiModel(text, "") {
    override fun getIconResId() = 0
}

abstract class JokeUiModel(private val text: String, private val punchline: String) {

    protected fun getJokeUi() = "$text\n$punchline"

    @DrawableRes
    protected abstract fun getIconResId(): Int

    fun map(callback: DataCallback) = callback.run {
        provideText(getJokeUi())
        provideIconRes(getIconResId())
    }
}