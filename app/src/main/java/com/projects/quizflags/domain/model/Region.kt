package com.projects.quizflags.domain.model

data class Region(
    val code: String,
    val nameIta: String,
    val nameEng: String,
    val nameEsp: String,
    val countriesCount: Int = 0
) {
    fun getLocalizedName(locale: String = "it"): String {
        return when (locale) {
            "en" -> nameEng
            "es" -> nameEsp
            else -> nameIta
        }
    }
}
