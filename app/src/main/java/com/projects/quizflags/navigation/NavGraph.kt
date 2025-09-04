package com.projects.quizflags.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.ui.view.EndGameScreen
import com.projects.quizflags.ui.view.GameScreen
import com.projects.quizflags.ui.view.HomeScreen
import com.projects.quizflags.ui.view.RegionChoiceScreen
import com.projects.quizflags.ui.viewModel.GameViewModel
import com.projects.quizflags.ui.viewModel.HomeViewModel
import com.projects.quizflags.ui.viewModel.RegionViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }

        composable("classic_game") {
            val gameViewModel: GameViewModel = hiltViewModel()

            GameScreen(
                navController = navController,
                gameViewModel = gameViewModel,
                mode = GameMode.ClassicGame
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
                navController = navController,
                gameViewModel = gameViewModel,
                mode = GameMode.SurvivalGame
            )
        }

        composable("region_game/{regionCode}") { backStackEntry ->
            val gameViewModel: GameViewModel = hiltViewModel()
            val regionCode = backStackEntry.arguments?.getString("regionCode") ?: "eu"

            GameScreen(
                navController = navController,
                gameViewModel = gameViewModel,
                mode = GameMode.RegionGame(regionCode)
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