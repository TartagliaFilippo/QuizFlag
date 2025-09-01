package com.projects.quizflags.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.projects.quizflags.R

// Font variabile (Regular + Italic)
val RobotoVariable = FontFamily(
    Font(R.font.roboto_variable) // normale
)

val RobotoVariableItalic = FontFamily(
    Font(R.font.roboto_italic_variable, style = FontStyle.Italic)
)

fun baseTypography(
    scale: Float = 1f
): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = RobotoVariable,
            fontWeight = FontWeight.Bold,
            fontSize = (32 * scale).sp,
            lineHeight = (40 * scale).sp
        ),
        titleLarge = TextStyle(
            fontFamily = RobotoVariable,
            fontWeight = FontWeight.SemiBold,
            fontSize = (20 * scale).sp,
            lineHeight = (28 * scale).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = RobotoVariable,
            fontWeight = FontWeight.Normal,
            fontSize = (16 * scale).sp,
            lineHeight = (24 * scale).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = RobotoVariableItalic,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = (14 * scale).sp,
            lineHeight = (20 * scale).sp
        ),
        labelSmall = TextStyle(
            fontFamily = RobotoVariable,
            fontWeight = FontWeight.Light,
            fontSize = (12 * scale).sp,
            lineHeight = (16 * scale).sp
        )
    )
}