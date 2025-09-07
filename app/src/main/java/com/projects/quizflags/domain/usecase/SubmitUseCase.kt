package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.repository.GameRepository
import javax.inject.Inject

class SubmitUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(selectedCountry: Country): Result<GameResult> {
        return gameRepository.submitAnswer(selectedCountry)
    }
}