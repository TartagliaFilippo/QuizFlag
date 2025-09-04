package com.projects.quizflags.data.local

import android.content.Context
import com.projects.quizflags.domain.model.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.projects.quizflags.R
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json

@Singleton
class CountriesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun loadCountries(): List<Country> = withContext(Dispatchers.IO) {
        val inputStream = context.resources.openRawResource(R.raw.countries)
        val json = inputStream.bufferedReader().use { it.readText() }
        Json {
            ignoreUnknownKeys = true
        }.decodeFromString<List<Country>>(json)
    }
}