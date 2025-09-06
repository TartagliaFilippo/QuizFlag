package com.projects.quizflags.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CountryButton(
    text: String = "Default",
    isCorrect: Boolean? = null,
    onClick: () -> Unit
) {
    val backgroundColor = when (isCorrect) {
        true -> Color(0xFF4CAF50)
        false -> Color(0xFFF44336)
        null -> Color.Blue
    }

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.9f),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}