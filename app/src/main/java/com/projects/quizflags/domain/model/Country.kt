package com.projects.quizflags.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val nameIta: String,
    val nameEng: String,
    val nameEsp: String,
    val code: String,
    val region: String,
    val pathImg: String
) {
    val flagResourceId: Int = 0
}