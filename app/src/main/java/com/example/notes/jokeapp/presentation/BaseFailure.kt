package com.example.notes.jokeapp.presentation

import com.example.notes.jokeapp.R
import com.example.notes.jokeapp.core.presentation.Failure
import com.example.notes.jokeapp.core.ResourceManager

abstract class BaseFailure(private val resourceManager: ResourceManager) : Failure {

    protected abstract fun getMessageResId(): Int

    override fun getMessage(): String = resourceManager.getString(getMessageResId())
}

class NoConnection(resourceManager: ResourceManager) : BaseFailure(resourceManager) {
    override fun getMessageResId() = R.string.no_connection
}

class ServiceUnavailable(resourceManager: ResourceManager) : BaseFailure(resourceManager) {
    override fun getMessageResId() = R.string.service_unavailable
}

class NoCachedData(resourceManager: ResourceManager) : BaseFailure(resourceManager) {
    override fun getMessageResId() = R.string.no_cached_item
}

class GenericError(resourceManager: ResourceManager) : BaseFailure(resourceManager) {
    override fun getMessageResId() = R.string.generic_fail_message
}
