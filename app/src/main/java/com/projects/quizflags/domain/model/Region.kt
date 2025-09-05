package com.projects.quizflags.domain.model

import com.projects.quizflags.R

enum class Region(
    val code: String,
    val nameRes: Int
) {
    EUROPE("eu", R.string.region_europe),
    ASIA("as", R.string.region_asia),
    AFRICA("af", R.string.region_africa),
    NORTH_AMERICA("na", R.string.region_north_america),
    SOUTH_AMERICA("sa", R.string.region_south_america),
    OCEANIA("oc", R.string.region_oceania),
    ANTARCTICA("an", R.string.region_antarctica);

    companion object {
        fun fromCode(code: String): Region? = values().find { it.code == code }
    }
}
