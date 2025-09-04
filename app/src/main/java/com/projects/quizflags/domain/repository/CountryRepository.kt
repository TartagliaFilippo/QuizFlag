package com.projects.quizflags.domain.repository

import com.projects.quizflags.domain.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountriesByRegion(regionCode: String): List<Country>
    fun getImageResourceId(pathImg: String): Int
}