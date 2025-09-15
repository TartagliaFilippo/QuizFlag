package com.projects.quizflags.presentation.ui.screen.home

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.responsive.DeviceType
import com.projects.quizflags.presentation.ui.responsive.calculateWindowSize
import com.projects.quizflags.presentation.ui.responsive.toDeviceType
import com.projects.quizflags.ui.theme.QuizFlagsTheme
import com.projects.quizflags.presentation.ui.layout.home.desktop.DesktopLayout
import com.projects.quizflags.presentation.ui.layout.home.phone.PhoneLandscapeLayout
import com.projects.quizflags.presentation.ui.layout.home.phone.PhonePortraitLayout
import com.projects.quizflags.presentation.ui.layout.home.tablet.TabletLandscapeLayout
import com.projects.quizflags.presentation.ui.layout.home.tablet.TabletPortraitLayout

@Composable
fun HomeScreen(
    onNavigateToGame: (GameMode) -> Unit,
    onNavigateToRegionChoice: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
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
                DeviceType.PhonePortrait -> PhonePortraitLayout(
                    games = games,
                    onNavigateToGame = onNavigateToGame,
                    onNavigateToRegionChoice = onNavigateToRegionChoice
                )
                DeviceType.PhoneLandscape -> PhoneLandscapeLayout(
                    games = games,
                    onNavigateToGame = onNavigateToGame,
                    onNavigateToRegionChoice = onNavigateToRegionChoice
                )
                DeviceType.TabletPortrait -> TabletPortraitLayout(
                    games = games,
                    onNavigateToGame = onNavigateToGame,
                    onNavigateToRegionChoice = onNavigateToRegionChoice
                )
                DeviceType.TabletLandscape -> TabletLandscapeLayout(
                    games = games,
                    onNavigateToGame = onNavigateToGame,
                    onNavigateToRegionChoice = onNavigateToRegionChoice
                )
                DeviceType.Desktop -> DesktopLayout(
                    games = games,
                    onNavigateToGame = onNavigateToGame,
                    onNavigateToRegionChoice = onNavigateToRegionChoice
                )
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

    QuizFlagsTheme(
        windowSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(360.dp, 640.dp)
        )
    ) {
        HomeScreen(
            onNavigateToGame = { /* Preview - no action */ },
            onNavigateToRegionChoice = { /* Preview - no action */ }
        )
    }
}