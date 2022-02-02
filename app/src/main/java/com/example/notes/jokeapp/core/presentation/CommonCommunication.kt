package com.example.notes.jokeapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.notes.jokeapp.presentation.BaseViewModel
import com.example.notes.jokeapp.presentation.CommonUiModel

interface CommonCommunication<T>: CommunicationList<T>, Communication {

}

