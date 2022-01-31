package com.example.notes.jokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.notes.jokeapp.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<CorrectProgress>(R.id.progressBar)
        val favoriteDataView = findViewById<FavoriteDataView>(R.id.jokeFavoriteDataView)
        viewModel = (application as MyApplication).viewModel

        progressBar.visibility = View.INVISIBLE

        favoriteDataView.listenChanges { isChecked ->
            viewModel.chooseFavorite(isChecked)
        }
        favoriteDataView.handleChangeButton {
            viewModel.changeJokeStatus()
        }
        favoriteDataView.handleActionButton {
            viewModel.getJoke()
        }

        viewModel.observe(this, { state ->
            favoriteDataView.show(state)
        })
    }
}