package com.projects.quizflags.presentation.ui.components.endgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.quizflags.presentation.ui.utility.getTrophyEmoji
import com.projects.quizflags.presentation.ui.utility.getTrophyTitle

@Composable
fun EndGameTrophy(score: Int, totalQuestions: Int) {
    val percentage = if (totalQuestions > 0) (score * 100) / totalQuestions else 0

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getTrophyEmoji(percentage),
            fontSize = 120.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = getTrophyTitle(percentage),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}