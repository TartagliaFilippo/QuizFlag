package com.projects.quizflags.data.repository

import android.content.Context
import com.projects.quizflags.data.datasource.CountriesDataSource
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.Region
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val countriesDataSource: CountriesDataSource,
    @ApplicationContext private val context: Context
): CountryRepository {
    private var cachedCountries: List<Country>? = null

    override suspend fun getAllCountries(): Result<List<Country>> {
        return try {
            val countries = cachedCountries ?: run {
                val loadedCountries = countriesDataSource.loadCountries()
                cachedCountries = loadedCountries
                loadedCountries
            }
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCountriesByRegion(regionCode: String): Result<List<Country>> {
        return getAllCountries().map { countries ->
            countries.filter { it.region.equals(regionCode, ignoreCase = true) }
                .sortedBy { it.nameIta }
        }
    }

    override suspend fun getRegions(): Result<List<Region>> {
        return getAllCountries().map { countries ->
            // Mappa predefinita di tutti i continenti
            val allRegions = mapOf(
                "an" to Region("an", "Antartide", "Antarctica", "Antártida"),
                "eu" to Region("eu", "Europa", "Europe", "Europa"),
                "af" to Region("af", "Africa", "Africa", "África"),
                "oc" to Region("oc", "Oceania", "Oceania", "Oceanía"),
                "na" to Region("na", "Nord America", "North America", "América del Norte"),
                "sa" to Region("sa", "Sud America", "South America", "Sudamérica"),
                "as" to Region("as", "Asia", "Asia", "Asia")
            )

            // Conta i paesi per ogni regione
            val regionCounts = countries.groupingBy { it.region }.eachCount()

            // Restituisce solo le regioni che hanno almeno un paese
            allRegions.values
                .map { region ->
                    region.copy(countriesCount = regionCounts[region.code] ?: 0)
                }
                .filter { it.countriesCount > 0 }
                .sortedBy { it.nameIta }
        }
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