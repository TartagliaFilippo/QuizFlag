package com.projects.quizflags.domain.model

sealed class GameMode {
    abstract val route: String
    abstract val totalQuestions: Int

    object ClassicGame : GameMode() {
        override val route = "classic_game"
        override val totalQuestions = 10
    }

    object SurvivalGame : GameMode() {
        override val route = "survival_game"
        override val totalQuestions = Int.MAX_VALUE // Valore infinito per il survival
    }

    data class RegionGame(val regionCode: String) : GameMode() {
        override val route = "region_game/$regionCode"
        override val totalQuestions = -1 // Calcolato direttamente dal repository
    }

}