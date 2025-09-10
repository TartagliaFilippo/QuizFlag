package com.projects.quizflags.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ClassicGame : Screen("classic_game")
    object SurvivalGame : Screen("survival_game")
    object RegionChoice : Screen("region_choice")

    object RegionGame : Screen("region_game/{regionCode}") {
        fun createRoute(regionCode: String) = "region_game/$regionCode"
    }

    object EndGame : Screen("endgame/{score}") {
        fun createRoute(score: Int) = "endgame/$score"
    }
}