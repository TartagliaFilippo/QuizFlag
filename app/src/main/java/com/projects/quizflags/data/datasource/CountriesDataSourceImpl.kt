package com.projects.quizflags.data.datasource

import android.content.Context
import com.projects.quizflags.R
import com.projects.quizflags.domain.model.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
): CountriesDataSource {
    override suspend fun loadCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            val inputStream = context.resources.openRawResource(R.raw.countries)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            json.decodeFromString<List<Country>>(jsonString)
        }
    }
}