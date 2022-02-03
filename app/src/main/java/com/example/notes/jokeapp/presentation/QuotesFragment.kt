package com.example.notes.jokeapp.presentation

import com.example.notes.jokeapp.R

class QuotesFragment : BaseFragment<QuotesViewModel, String>() {
    override fun checkBoxText() = R.string.show_favorite_quote
    override fun actionButtonText() = R.string.button_get_quote
    override fun getViewModelClass() = QuotesViewModel::class.java
}