package com.example.notes.jokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.jokeapp.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val favoriteDataView = findViewById<FavoriteDataView>(R.id.jokeFavoriteDataView)
        val viewModel = (application as MyApplication).viewModel
        favoriteDataView.linkWith(viewModel)

        viewModel.observe(this, { state ->
            favoriteDataView.show(state)
        })

        val quoteFavoriteDataModel = findViewById<FavoriteDataView>(R.id.quoteFavoriteDataView)
        val quoteViewModel = (application as MyApplication).quoteViewModel
        quoteFavoriteDataModel.linkWith(quoteViewModel)
        quoteViewModel.observe(this, { state ->
            quoteFavoriteDataModel.show(state)
        })
    }
}