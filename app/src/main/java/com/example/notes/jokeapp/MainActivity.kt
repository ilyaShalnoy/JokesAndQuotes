package com.example.notes.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val button = findViewById<Button>(R.id.button)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val iconView = findViewById<ImageButton>(R.id.iconView)

        progressBar.visibility = View.INVISIBLE

        viewModel = (application as MyApplication).viewModel

        button.setOnClickListener {
            button.isEnabled = false
            progressBar.visibility = View.VISIBLE
            viewModel?.getJoke()
        }

        checkBox.setOnCheckedChangeListener { _, checked ->
            viewModel?.chooseFavorite(checked)
        }

        iconView.setOnClickListener {
            viewModel?.changeJokeStatus()
        }

        viewModel?.init(object : DataCallback {
            override fun provideText(text: String) {
                progressBar.visibility = View.INVISIBLE
                button.isEnabled = true
                textView.text = text
            }

            override fun provideIconRes(id: Int) {
                iconView.setImageResource(id)
            }
        })
    }

    override fun onDestroy() {
        viewModel?.clear()
        super.onDestroy()
    }
}