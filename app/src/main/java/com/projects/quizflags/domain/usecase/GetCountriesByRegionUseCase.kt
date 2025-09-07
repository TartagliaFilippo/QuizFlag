package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.Country
import com.projects.quizflags.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountriesByRegionUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(regionCode: String): Result<List<Country>> {
        return repository.getCountriesByRegion(regionCode)
    }
}