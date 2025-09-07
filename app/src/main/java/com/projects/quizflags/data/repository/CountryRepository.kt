package com.projects.quizflags.data.repository

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.Region

interface CountryRepository {
    suspend fun getAllCountries(): Result<List<Country>>
    suspend fun getCountriesByRegion(regionCode: String): Result<List<Country>>
    suspend fun getRegions(): Result<List<Region>>
    fun getImageResourceId(pathImg: String): Int
}