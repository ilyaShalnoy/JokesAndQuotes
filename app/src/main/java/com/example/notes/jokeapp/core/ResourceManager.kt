package com.example.notes.jokeapp.core

import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes stringResId: Int): String

}

