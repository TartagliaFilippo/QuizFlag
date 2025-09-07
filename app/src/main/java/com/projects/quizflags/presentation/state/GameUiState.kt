package com.projects.quizflags.presentation.state

import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState

data class GameUiState(
    val gameState: GameState = GameState(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastResult: GameResult? = null
)
