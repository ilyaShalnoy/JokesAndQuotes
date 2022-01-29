package com.example.notes.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<CorrectTextView>(R.id.textView)
        val progressBar = findViewById<CorrectProgress>(R.id.progressBar)
        val button = findViewById<CorrectButton>(R.id.button)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val iconView = findViewById<CorrectImageButton>(R.id.iconView)

        progressBar.visibility = View.INVISIBLE

        viewModel = (application as MyApplication).viewModel

        button.setOnClickListener {
            viewModel.getJoke()
        }

        checkBox.setOnCheckedChangeListener { _, checked ->
            viewModel.chooseFavorite(checked)
        }

        iconView.setOnClickListener {
            viewModel.changeJokeStatus()
        }

        viewModel.observe(this, { state ->
            state.show(progressBar, button, textView, iconView)
        })
    }
}