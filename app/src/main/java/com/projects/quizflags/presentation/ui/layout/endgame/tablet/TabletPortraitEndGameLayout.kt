package com.projects.quizflags.presentation.ui.layout.endgame.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.quizflags.domain.model.GameMode
import com.projects.quizflags.presentation.ui.components.endgame.EndGameActions
import com.projects.quizflags.presentation.ui.components.endgame.EndGameBackground
import com.projects.quizflags.presentation.ui.components.endgame.EndGameHeader
import com.projects.quizflags.presentation.ui.components.endgame.EndGameStats
import com.projects.quizflags.presentation.ui.responsive.ActionOrientation
import com.projects.quizflags.presentation.ui.responsive.StatsOrientation

@Composable
fun TabletPortraitEndGameLayout(
    score: Int,
    totalQuestions: Int,
    gameMode: GameMode,
    onPlayAgain: () -> Unit,
    onNavigateHome: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        EndGameBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.widthIn(max = 600.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EndGameHeader(
                        score = score,
                        totalQuestions = totalQuestions,
                        gameMode = gameMode,
                        isCompact = false
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    EndGameStats(
                        score = score,
                        totalQuestions = totalQuestions,
                        gameMode = gameMode,
                        orientation = StatsOrientation.HORIZONTAL
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    EndGameActions(
                        onPlayAgain = onPlayAgain,
                        onNavigateHome = onNavigateHome,
                        orientation = ActionOrientation.HORIZONTAL,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}