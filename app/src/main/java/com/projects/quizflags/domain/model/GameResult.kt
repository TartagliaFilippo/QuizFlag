package com.projects.quizflags.domain.model

data class GameResult(
    val isCorrect: Boolean,
    val correctAnswer: Country,
    val selectedAnswer: Country
)
