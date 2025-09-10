package com.projects.quizflags.ui.view.home.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.GamesRoute
import com.projects.quizflags.ui.components.ModeButtonLandscape

@Composable
fun PhoneLandscapeLayout(
    games: List<GamesRoute>,
    navController: NavHostController
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background_green),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        games.forEach { game ->
            ModeButtonLandscape(
                icon = game.icon,
                iconDescription = game.name,
                mode = game.name,
                onClick = {
                    val route = when (val mode = game.mode) {
                        null -> "region_choice"
                        else -> mode.route
                    }
                    navController.navigate(route)
                },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }
}