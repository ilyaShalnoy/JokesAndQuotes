package com.example.notes.jokeapp.presentation

import com.example.notes.jokeapp.MyApplication
import com.example.notes.jokeapp.R

class JokesFragment : BaseFragment<Int>() {
    override fun getViewModel(app: MyApplication) = app.viewModel
    override fun getCommunication(app: MyApplication) = app.jokeCommunication
    override fun checkBoxText() = R.string.show_favorite_joke
    override fun actionButtonText() = R.string.button_get_joke
}