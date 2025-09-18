package com.projects.quizflags.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.screen.game.GameScreen
import com.projects.quizflags.presentation.ui.screen.endgame.EndGameScreen
import com.projects.quizflags.presentation.ui.screen.home.HomeScreen
import com.projects.quizflags.presentation.ui.screen.region.RegionChoiceScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {

            HomeScreen(
                onNavigateToGame = { gameMode ->
                    when (gameMode) {
                        is GameMode.ClassicGame -> {
                            navController.navigate(Screen.ClassicGame.route)
                        }
                        is GameMode.SurvivalGame -> {
                            navController.navigate(Screen.SurvivalGame.route)
                        }
                        is GameMode.RegionGame -> {
                            navController.navigate(Screen.RegionGame.createRoute(gameMode.regionCode))
                        }
                    }
                },
                onNavigateToRegionChoice = {
                    navController.navigate(Screen.RegionChoice.route)
                }
            )
        }

        composable(Screen.ClassicGame.route) {

            GameScreen(
                gameMode = GameMode.ClassicGame,
                onNavigateToEndGame = { score, totalQuestions, gameMode ->
                    navController.navigateToEndGame(score, totalQuestions, gameMode)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable(Screen.SurvivalGame.route) {

            GameScreen(
                gameMode = GameMode.SurvivalGame,
                onNavigateToEndGame = { score, totalQuestions, gameMode ->
                    navController.navigateToEndGame(score, totalQuestions, gameMode)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable(Screen.RegionChoice.route) {

            RegionChoiceScreen(
                onRegionSelected = { regionCode ->
                    navController.navigate(Screen.RegionGame.createRoute(regionCode))
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable(
            route = Screen.RegionGame.route,
            arguments = listOf(
                navArgument("regionCode") {
                    type = NavType.StringType
                    defaultValue = "eu"
                }
            )
        ) { backStackEntry ->
            val regionCode = backStackEntry.arguments?.getString("regionCode") ?: "eu"

            GameScreen(
                gameMode = GameMode.RegionGame(regionCode),
                onNavigateToEndGame = { score, totalQuestions, gameMode ->
                    navController.navigateToEndGame(score, totalQuestions, gameMode)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable(
            route = Screen.EndGame.route,
            arguments = listOf(
                navArgument("score") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 10
            val gameModeString = backStackEntry.arguments?.getString("gameMode") ?: "classic"

            val gameMode = when (gameModeString) {
                "classic" -> GameMode.ClassicGame
                "survival" -> GameMode.SurvivalGame
                else -> GameMode.RegionGame(gameModeString)
            }

            EndGameScreen(
                score = score,
                totalQuestions = totalQuestions,
                gameMode = gameMode,
                onPlayAgain = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                },
                onNavigateHome = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }
    }
}