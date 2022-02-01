package com.example.notes.jokeapp.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.notes.jokeapp.core.presentation.EnableView
import com.example.notes.jokeapp.core.presentation.ShowImage
import com.example.notes.jokeapp.core.presentation.ShowText
import com.example.notes.jokeapp.core.presentation.ShowView

class CorrectTextView : AppCompatTextView, ShowText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)


    override fun show(text: String) {
        setText(text)
    }

}

class CorrectImageButton : AppCompatImageButton, ShowImage {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun show(id: Int) {
        setImageResource(id)
    }
}

class CorrectButton: AppCompatButton, EnableView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun enable(enable: Boolean) {
        isEnabled = enable
    }
}

class CorrectProgress: ProgressBar, ShowView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun show(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}