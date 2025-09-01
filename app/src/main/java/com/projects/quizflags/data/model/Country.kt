package com.projects.quizflags.data.model

import kotlinx.serialization.Serializable

/*@Serializable
data class Country(
    val nameEng: String,
    val nameEsp: String,
    val nameIta: String,
    val code: String,
    val region: String,
    val pathImg: String
)*/

@Serializable
data class Country(
    val nameIta: String,
    val nameEng: String,
    val nameEsp: String,
    val code: String,
    val region: String,
    val pathImg: String
)