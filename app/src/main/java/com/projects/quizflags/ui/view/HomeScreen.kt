package com.projects.quizflags.ui.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GamesRoute
import com.projects.quizflags.ui.responsive.DeviceType
import com.projects.quizflags.ui.responsive.calculateWindowSize
import com.projects.quizflags.ui.responsive.toDeviceType
import com.projects.quizflags.ui.theme.QuizFlagsTheme
import com.projects.quizflags.ui.view.home.desktop.DesktopLayout
import com.projects.quizflags.ui.view.home.phone.PhoneLandscapeLayout
import com.projects.quizflags.ui.view.home.phone.PhonePortraitLayout
import com.projects.quizflags.ui.view.home.tablet.TabletLandscapeLayout
import com.projects.quizflags.ui.view.home.tablet.TabletPortraitLayout
import com.projects.quizflags.ui.viewModel.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val games by homeViewModel.games.collectAsStateWithLifecycle()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) { with(this) {

        val windowSize = calculateWindowSize(
            maxWidth = maxWidth,
            maxHeight =  maxHeight
        )

        val deviceType = toDeviceType(
            windowSize,
            maxWidth,
            maxHeight
        )

            when (deviceType){
                DeviceType.PhonePortrait -> PhonePortraitLayout(games, navController)
                DeviceType.PhoneLandscape -> PhoneLandscapeLayout(games, navController)
                DeviceType.TabletPortrait -> TabletPortraitLayout(games, navController)
                DeviceType.TabletLandscape -> TabletLandscapeLayout(games, navController)
                DeviceType.Desktop -> DesktopLayout(games, navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Mobile",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Preview(
    name = "Tablet",
    showBackground = true,
    widthDp = 800,
    heightDp = 1280
)
@Preview(
    name = "Desktop",
    showBackground = true,
    widthDp = 1200,
    heightDp = 800
)
@Composable
fun PreviewResponsiveHomeScreen() {
    val fakeGames = listOf(
        GamesRoute(id = 1,R.string.game_classic, GameMode.ClassicGame, R.drawable.home_startflag),
        GamesRoute(id = 2,R.string.game_survival, GameMode.SurvivalGame, R.drawable.home_survival)
    )
    val fakeViewModel = object : HomeViewModel() {
        override val games = MutableStateFlow(fakeGames)
    }

    QuizFlagsTheme(
        windowSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(360.dp, 640.dp) // qui puoi giocare coi valori
        )
    ) {
        HomeScreen(
            navController = rememberNavController(),
            homeViewModel = fakeViewModel
        )
    }
}