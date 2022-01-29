package com.example.notes.jokeapp

import androidx.annotation.DrawableRes

interface Show<T> {
    fun show(arg: T)
}

interface ShowView : Show<Boolean> {
}

interface ShowText : Show<String> {
}

interface ShowImage : Show<Int> {
}

interface EnableView {
    fun enable(enable: Boolean)
}