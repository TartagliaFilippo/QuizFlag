package com.projects.quizflags.domain.model

data class GameState(
    val currentQuestion: QuizQuestion? = null,
    val score: Int = 0,
    val round: Int = 0,
    val isGameOver: Boolean = false,
    val gameMode: GameMode? = null,
    val totalQuestions: Int = 0
) {
    // Calcolo la percentuale del gioco
    val percentage: Int
        get() = if (totalQuestions > 0) (score * 100) / totalQuestions else 0

    // Verifico che il gioco è completato (non fallito)
    val isCompleted: Boolean
        get() = isGameOver && (gameMode !is GameMode.SurvivalGame || score > 0)
}
