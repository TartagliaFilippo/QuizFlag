package com.projects.quizflags.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.screen.game.GameScreen
import com.projects.quizflags.ui.view.EndGameScreen
import com.projects.quizflags.ui.view.HomeScreen
import com.projects.quizflags.ui.view.RegionChoiceScreen
import com.projects.quizflags.presentation.ui.screen.game.GameViewModel
import com.projects.quizflags.presentation.ui.screen.home.HomeViewModel
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
            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable(Screen.ClassicGame.route) {
            val gameViewModel: GameViewModel = hiltViewModel()

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

        composable("survival_game") {
            val gameViewModel: GameViewModel = hiltViewModel()

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

        composable("region_game/{regionCode}") { backStackEntry ->
            val gameViewModel: GameViewModel = hiltViewModel()
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
                navController = navController,
                score = score
            )
        }
    }
}