package com.example.notes.jokeapp

import android.content.Context
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes stringResId: Int): String

}

class BaseResourcesManager(private val context: Context): ResourceManager {
    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

}