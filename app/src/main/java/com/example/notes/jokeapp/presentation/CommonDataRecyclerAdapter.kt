package com.example.notes.jokeapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.core.presentation.CommonCommunication

class CommonDataRecyclerAdapter<T>(
    private val listener: FavoriteItemClickListener<T>,
    private val communicationList: CommonCommunication<T>
) : RecyclerView.Adapter<CommonDataRecyclerAdapter.CommonDataViewHolder<T>>() {

    interface FavoriteItemClickListener<T> {
        fun change(id: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonDataViewHolder<T> {
        val emptyList = viewType == 0
        val view = LayoutInflater.from(parent.context).inflate(
            if (emptyList) {
                R.layout.no_favorite_item
            } else {
                R.layout.common_data_item
            }, parent, false
        )
        return if (emptyList) EmptyFavoritesViewHolder(view) else CommonDataViewHolder.Base(view, listener)
    }

    override fun onBindViewHolder(holder: CommonDataViewHolder<T>, position: Int) {
        holder.bind(communicationList.getList()[position])
    }

    override fun getItemCount() = communicationList.getList().size

    override fun getItemViewType(position: Int) = when (communicationList.getList()[position]) {
        is FailedCommonUiModel -> 0
        else -> 1
    }

    fun update() {
        communicationList.getDiffResult().dispatchUpdatesTo(this)
    }

    abstract class CommonDataViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = itemView.findViewById<CorrectTextView>(R.id.commonDataTextView)

        open fun bind(model: CommonUiModel<T>) {
            model.show(textView)
        }

        class Base<T>(view: View, private val listener: FavoriteItemClickListener<T>) : CommonDataViewHolder<T>(view) {
            private val iconView = itemView.findViewById<CorrectImageButton>(R.id.iconView)

            override fun bind(model: CommonUiModel<T>) {
                super.bind(model)
                iconView.setOnClickListener {
                    model.change(listener)
                }
            }
        }

    }

    inner class EmptyFavoritesViewHolder<T>(view: View) : CommonDataViewHolder<T>(view) {

    }
}