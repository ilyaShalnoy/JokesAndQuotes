package com.example.notes.jokeapp.domain

import com.example.notes.jokeapp.core.Mapper
import com.example.notes.jokeapp.core.presentation.Failure
import com.example.notes.jokeapp.presentation.*

sealed class CommonItem() : Mapper<CommonUiModel> {
    class Success(
        private val firstText: String,
        private val secondText: String,
        private val favorite: Boolean
    ) : CommonItem() {

        override fun to(): CommonUiModel {
            return if (favorite) {
                FavoriteCommonUiModel(firstText, secondText)
            } else {
                BaseCommonUiModel(firstText, secondText)
            }
        }
    }

    class Failed(private val failure: Failure) : CommonItem() {
        override fun to(): CommonUiModel {
            return FailedCommonUiModel(failure.getMessage())
        }
    }
}