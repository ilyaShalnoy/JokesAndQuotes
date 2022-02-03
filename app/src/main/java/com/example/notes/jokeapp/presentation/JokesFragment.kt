package com.example.notes.jokeapp.presentation

import com.example.notes.jokeapp.R

class JokesFragment : BaseFragment<JokesViewModel, Int>() {
    override fun checkBoxText() = R.string.show_favorite_joke
    override fun actionButtonText() = R.string.button_get_joke
    override fun getViewModelClass() = JokesViewModel::class.java
}