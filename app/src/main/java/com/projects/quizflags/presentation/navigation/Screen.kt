package com.projects.quizflags.presentation.navigation

import com.projects.quizflags.domain.model.GameMode

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ClassicGame : Screen("classic_game")
    object SurvivalGame : Screen("survival_game")
    object RegionChoice : Screen("region_choice")

    object RegionGame : Screen("region_game/{regionCode}") {
        fun createRoute(regionCode: String) = "region_game/$regionCode"
    }

    object EndGame : Screen("endgame/{score}/{totalQuestions}/{gameMode}") {
        fun createRoute(score: Int, totalQuestions: Int, gameMode: GameMode): String {
            val gameModeString = when (gameMode) {
                is GameMode.ClassicGame -> "classic"
                is GameMode.SurvivalGame -> "survival"
                is GameMode.RegionGame -> gameMode.regionCode
            }
            return "endgame/$score/$totalQuestions/$gameModeString"
        }
    }
}