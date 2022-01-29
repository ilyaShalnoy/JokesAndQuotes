package com.example.notes.jokeapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

class CorrectProgress: ProgressBar, ShowView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun show(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

}