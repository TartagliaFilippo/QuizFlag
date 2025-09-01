package com.projects.quizflags.data.model

data class QuizQuestion(
    val correctAnswer: Country,
    val options: List<Country>
)
