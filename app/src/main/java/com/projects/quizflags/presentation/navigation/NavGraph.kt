package com.projects.quizflags.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.screen.game.GameScreen
import com.projects.quizflags.ui.view.EndGameScreen
import com.projects.quizflags.presentation.ui.screen.home.HomeScreen
import com.projects.quizflags.ui.view.RegionChoiceScreen
import com.projects.quizflags.presentation.ui.screen.region.RegionViewModel

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
                        is GameMode.RegionGame -> {
                            navController.navigate(Screen.RegionGame.createRoute(gameMode.regionCode))
                        }
                        is GameMode.SurvivalGame -> {
                            navController.navigate(Screen.SurvivalGame.route)
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
                onNavigateToEndGame = { score ->
                    navController.navigateToEndGame(score)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable("region_choice") {
            val regionViewModel: RegionViewModel = hiltViewModel()

            RegionChoiceScreen(
                navController = navController,
                regionViewModel = regionViewModel
            )
        }

        composable(Screen.SurvivalGame.route) {

            GameScreen(
                gameMode = GameMode.SurvivalGame,
                onNavigateToEndGame = { score ->
                    navController.navigateToEndGame(score)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable("region_game/{regionCode}") { backStackEntry ->
            val regionCode = backStackEntry.arguments?.getString("regionCode") ?: "eu"

            GameScreen(
                gameMode = GameMode.RegionGame(regionCode),
                onNavigateToEndGame = { score ->
                    navController.navigateToEndGame(score)
                },
                onNavigateBack = {
                    navController.navigateBack()
                }
            )
        }

        composable("endGame/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0

            EndGameScreen(
                score = score,
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