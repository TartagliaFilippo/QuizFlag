package com.projects.quizflags.domain

import com.projects.quizflags.data.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountriesByRegion(regionCode: String): List<Country>
    fun getImageResourceId(pathImg: String): Int
}