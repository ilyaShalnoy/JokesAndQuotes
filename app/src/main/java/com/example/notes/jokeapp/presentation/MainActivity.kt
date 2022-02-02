package com.example.notes.jokeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.jokeapp.*
import com.example.notes.jokeapp.data.CommonDataModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CommonDataRecyclerAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokeCommunication = (application as MyApplication).jokeCommunication
        val viewModel = (application as MyApplication).viewModel
        val favoriteDataView = findViewById<FavoriteDataView>(R.id.jokeFavoriteDataView)
        favoriteDataView.linkWith(viewModel)

        val observer: (t: List<CommonUiModel<Int>>) -> Unit = { _ ->
            adapter.update()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = CommonDataRecyclerAdapter(object : CommonDataRecyclerAdapter.FavoriteItemClickListener<Int> {
            override fun change(id: Int) {
                Snackbar.make(
                    favoriteDataView,
                    R.string.remove_from_favorites,
                    Snackbar.LENGTH_SHORT
                ).setAction(
                    R.string.yes
                ) {
                    val position = viewModel.changeItemStatus(id, this@MainActivity, observer)
                    adapter.update(Pair(false, position))
                }.show()
            }
        }, jokeCommunication)


        recyclerView.adapter = adapter

        viewModel.observeList(this, observer)

        viewModel.getItemList()

        viewModel.observe(this, { state ->
            favoriteDataView.show(state)
        })

        /* val quoteFavoriteDataModel = findViewById<FavoriteDataView>(R.id.quoteFavoriteDataView)
         val quoteViewModel = (application as MyApplication).quoteViewModel
         quoteFavoriteDataModel.linkWith(quoteViewModel)
         quoteViewModel.observe(this, { state ->
             quoteFavoriteDataModel.show(state)
         })*/
    }
}