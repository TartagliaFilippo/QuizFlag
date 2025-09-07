package com.projects.quizflags.domain.datasource

import com.projects.quizflags.domain.model.Country

interface CountriesDataSource {
    suspend fun loadCountries(): List<Country>
}