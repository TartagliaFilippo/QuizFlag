package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.domain.repository.GameRepository
import javax.inject.Inject

class StartGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(gameMode: GameMode): Result<QuizQuestion> {
        return gameRepository.startNewGame(gameMode)
    }
}