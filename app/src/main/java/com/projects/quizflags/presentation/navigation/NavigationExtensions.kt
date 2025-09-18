package com.projects.quizflags.presentation.navigation

import androidx.navigation.NavHostController
import com.projects.quizflags.domain.model.GameMode

fun NavHostController.navigateToGame(gameMode: GameMode) {
    when (gameMode) {
        is GameMode.ClassicGame -> navigate(Screen.ClassicGame.route)
        is GameMode.RegionGame -> navigate(Screen.RegionGame.createRoute(gameMode.regionCode))
        is GameMode.SurvivalGame -> navigate(Screen.SurvivalGame.route)
    }
}

fun NavHostController.navigateToEndGame(score: Int, totalQuestions: Int, gameMode: GameMode) {
    navigate(Screen.EndGame.createRoute(score, totalQuestions, gameMode)) {
        popUpTo(Screen.Home.route)
    }
}

fun NavHostController.navigateBack() {
    popBackStack()
}