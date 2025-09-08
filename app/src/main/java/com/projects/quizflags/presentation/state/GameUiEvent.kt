package com.projects.quizflags.presentation.state

import com.projects.quizflags.domain.model.Country

sealed class GameUiEvent {
    object StartGame : GameUiEvent()
    data class AnswerSelected(val country: Country) : GameUiEvent()
    object NextQuestion : GameUiEvent()
    object ClearError : GameUiEvent()
    object RetryGame : GameUiEvent()
}