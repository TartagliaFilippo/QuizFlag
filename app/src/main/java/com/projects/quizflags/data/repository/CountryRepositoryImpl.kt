package com.projects.quizflags.data.repository

import android.content.Context
import com.projects.quizflags.data.local.CountriesDataSource
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.repository.CountryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val countriesDataSource: CountriesDataSource,
    @ApplicationContext private val context: Context
): CountryRepository {
    private var cachedCountries: List<Country>? = null

    override suspend fun getAllCountries(): List<Country> {
        return cachedCountries ?: run {
            val countries = countriesDataSource.loadCountries()
            cachedCountries = countries
            countries
        }
    }

    override suspend fun getCountriesByRegion(regionCode: String): List<Country> {
        return getAllCountries().filter { it.region == regionCode }
    }

    override fun getImageResourceId(pathImg: String): Int {
        val imageName = pathImg.substringBeforeLast(".")
        return context.resources.getIdentifier(
            imageName,
            "drawable",
            context.packageName
        )
    }
}