package com.example.notes.jokeapp

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CorrectTextView : AppCompatTextView, ShowText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)


    override fun show(text: String) {
        setText(text)
    }

}