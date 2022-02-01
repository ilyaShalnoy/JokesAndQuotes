package com.example.notes.jokeapp.core.presentation


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