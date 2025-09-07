package com.projects.quizflags.domain.repository

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState
import com.projects.quizflags.domain.model.QuizQuestion

interface GameRepository {
    suspend fun startNewGame(gameMode: GameMode): Result<QuizQuestion>
    suspend fun getNextQuestion(): Result<QuizQuestion?>
    suspend fun submitAnswer(selectedCountry: Country): Result<GameResult>
    suspend fun getCurrentGameState(): GameState
    suspend fun resetGame()
}