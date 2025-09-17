package com.projects.quizflags.presentation.ui.layout.endgame.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun PhonePortraitEndGameLayout(
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EndGameHeader(
                score = score,
                totalQuestions = totalQuestions,
                gameMode = gameMode,
                isCompact = false
            )

            Spacer(modifier = Modifier.height(40.dp))

            EndGameStats(
                score = score,
                totalQuestions = totalQuestions,
                gameMode = gameMode,
                orientation = StatsOrientation.VERTICAL
            )

            Spacer(modifier = Modifier.height(40.dp))

            EndGameActions(
                onPlayAgain = onPlayAgain,
                onNavigateHome = onNavigateHome,
                orientation = ActionOrientation.VERTICAL,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}