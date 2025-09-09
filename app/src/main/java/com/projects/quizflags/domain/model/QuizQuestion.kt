package com.projects.quizflags.domain.model

data class QuizQuestion(
    val correctAnswer: Country,
    val options: List<Country>,
    val flagResourceId: Int
)
