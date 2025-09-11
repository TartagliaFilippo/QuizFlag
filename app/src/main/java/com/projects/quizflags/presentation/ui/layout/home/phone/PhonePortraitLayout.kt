package com.projects.quizflags.presentation.ui.layout.home.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.domain.model.GamesRoute
import com.projects.quizflags.ui.components.ModeButton

@Composable
fun PhonePortraitLayout(
    games: List<GamesRoute>,
    onNavigateToGame: (GameMode) -> Unit,
    onNavigateToRegionChoice: () -> Unit
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_green),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        items(games) { game ->
            ModeButton(
                icon = game.icon,
                iconDescription = game.name,
                mode = game.name,
                onClick = {
                    when (val mode = game.mode) {
                        null -> onNavigateToRegionChoice()
                        else -> onNavigateToGame(mode)
                    }
                }
            )
        }
    }
}