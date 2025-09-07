package com.projects.quizflags.domain.model

data class GameState(
    val currentQuestion: QuizQuestion? = null,
    val score: Int = 0,
    val round: Int = 0,
    val isGameOver: Boolean = false,
    val gameMode: GameMode? = null
)
