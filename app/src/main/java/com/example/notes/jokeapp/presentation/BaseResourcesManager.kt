package com.example.notes.jokeapp.presentation

import android.content.Context
import com.example.notes.jokeapp.core.ResourceManager

class BaseResourcesManager(private val context: Context): ResourceManager {
    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

}