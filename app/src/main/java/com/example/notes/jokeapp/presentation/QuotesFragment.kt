package com.example.notes.jokeapp.presentation

import com.example.notes.jokeapp.MyApplication
import com.example.notes.jokeapp.R

class QuotesFragment : BaseFragment<String>() {
    override fun getViewModel(app: MyApplication) = app.quoteViewModel
    override fun getCommunication(app: MyApplication) = app.quoteCommunication
    override fun checkBoxText() = R.string.show_favorite_quote
    override fun actionButtonText() = R.string.button_get_quote
}