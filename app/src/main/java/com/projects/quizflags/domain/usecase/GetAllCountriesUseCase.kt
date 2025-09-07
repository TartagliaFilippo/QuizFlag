package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.repository.CountryRepository
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getAllCountries()
    }
}