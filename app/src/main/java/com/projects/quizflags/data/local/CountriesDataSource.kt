package com.projects.quizflags.data.local

import android.content.Context
import com.projects.quizflags.domain.model.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.projects.quizflags.R
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Singleton
class CountriesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun loadCountries(): List<Country> = withContext(Dispatchers.IO) {
        val inputStream = context.resources.openRawResource(R.raw.countries)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        json.decodeFromString<List<Country>>(jsonString)
    }
}