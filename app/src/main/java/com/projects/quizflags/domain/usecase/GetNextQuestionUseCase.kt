package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.domain.repository.GameRepository
import javax.inject.Inject

class GetNextQuestionUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): Result<QuizQuestion?> {
        return gameRepository.getNextQuestion()
    }
}