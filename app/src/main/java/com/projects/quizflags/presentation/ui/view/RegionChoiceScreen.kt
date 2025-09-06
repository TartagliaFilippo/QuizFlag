package com.projects.quizflags.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.projects.quizflags.ui.viewModel.RegionViewModel

@Composable
fun RegionChoiceScreen(
    navController: NavHostController,
    regionViewModel: RegionViewModel
) {
    val regions by regionViewModel.regions.collectAsStateWithLifecycle()

    Column {
        regions.forEach { region ->
            Button(
                onClick = {
                    navController.navigate("region_game/${region.code}")
                }
            ) {
                Text(text = region.nameIta)
            }
        }
    }
}