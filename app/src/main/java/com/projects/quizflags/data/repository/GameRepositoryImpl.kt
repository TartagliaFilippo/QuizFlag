package com.projects.quizflags.data.repository

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GameResult
import com.projects.quizflags.domain.model.GameState
import com.projects.quizflags.domain.model.QuizQuestion
import com.projects.quizflags.domain.repository.CountryRepository
import com.projects.quizflags.domain.repository.GameRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val countryRepository: CountryRepository
): GameRepository {

    private var availableCountries: MutableList<Country> = mutableListOf()
    private var allCountries: List<Country> = emptyList()
    private var gameState = GameState()

    override suspend fun startNewGame(gameMode: GameMode): Result<QuizQuestion> {
        return try {
            gameState = GameState(gameMode = gameMode)

            val countriesResult = when (gameMode) {
                is GameMode.ClassicGame -> countryRepository.getAllCountries()
                is GameMode.RegionGame -> countryRepository.getCountriesByRegion(gameMode.regionCode)
                is GameMode.SurvivalGame -> countryRepository.getAllCountries()
            }

            countriesResult.fold(
                onSuccess = { countries ->
                    allCountries = countries

                    // Calcolo il numero totale di domande basato sulla modalità
                    val totalQuestions = when (gameMode) {
                        is GameMode.ClassicGame -> gameMode.totalQuestions
                        is GameMode.SurvivalGame -> countries.size
                        is GameMode.RegionGame -> countries.size
                    }

                    availableCountries = when (gameMode) {
                        is GameMode.ClassicGame -> countries.shuffled().take(gameMode.totalQuestions).toMutableList()
                        is GameMode.SurvivalGame -> countries.toMutableList()
                        is GameMode.RegionGame -> countries.toMutableList()
                    }

                    gameState = GameState(
                        gameMode = gameMode,
                        totalQuestions = totalQuestions
                    )

                    generateNewQuestion()
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNextQuestion(): Result<QuizQuestion?> {
        return try {
            if (shouldGameEnd()) {
                gameState = gameState.copy(isGameOver = true)
                Result.success(null)
            } else {
                generateNewQuestion()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitAnswer(selectedCountry: Country): Result<GameResult> {
        return try {
            val currentQuestion = gameState.currentQuestion
                ?: return Result.failure(IllegalStateException("No current question"))

            val isCorrect = selectedCountry.code == currentQuestion.correctAnswer.code
            val newScore = if (isCorrect) gameState.score + 1 else gameState.score

            gameState = gameState.copy(score = newScore)

            if (gameState.gameMode is GameMode.SurvivalGame && !isCorrect) {
                gameState = gameState.copy(isGameOver = true)
            }

            Result.success(
                GameResult(
                    isCorrect = isCorrect,
                    correctAnswer = currentQuestion.correctAnswer,
                    selectedAnswer = selectedCountry
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentGameState(): GameState = gameState

    override suspend fun resetGame() {
        gameState = GameState()
        availableCountries.clear()
        allCountries = emptyList()
    }

    private fun generateNewQuestion(): Result<QuizQuestion> {
        return try {
            if (availableCountries.isEmpty()) {
                gameState = gameState.copy(isGameOver = true)
                return Result.failure(IllegalStateException("No more countries available"))
            }

            val correctAnswer = availableCountries.random()
            availableCountries.remove(correctAnswer)

            val wrongAnswers = allCountries
                .filter { it.code != correctAnswer.code }
                .shuffled()
                .take(3)

            val options = (wrongAnswers + correctAnswer).shuffled()

            val flagResourceId = countryRepository.getImageResourceId(correctAnswer.pathImg)

            val question = QuizQuestion(
                correctAnswer = correctAnswer,
                options = options,
                flagResourceId = flagResourceId
            )

            gameState = gameState.copy(
                currentQuestion = question,
                round = gameState.round + 1
            )

            Result.success(question)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun shouldGameEnd(): Boolean {
        return when (gameState.gameMode) {
            is GameMode.ClassicGame -> gameState.round >= gameState.totalQuestions
            is GameMode.RegionGame -> availableCountries.isEmpty()
            is GameMode.SurvivalGame -> gameState.isGameOver
            null -> true
        }
    }
}