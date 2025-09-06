package com.projects.quizflags.data.repository

import android.content.Context
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.model.Region
import com.projects.quizflags.domain.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepositoryImpl @Inject constructor(
    private val context: Context,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
): CountryRepository {
    private var cachedCountries: List<Country>? = null

    override suspend fun getAllCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            if (cachedCountries == null) {
                val inputStream = context.resources.openRawResource(R.raw.countries)
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                cachedCountries = json.decodeFromString<List<Country>>(jsonString)
            }
            Result.success(cachedCountries!!)
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