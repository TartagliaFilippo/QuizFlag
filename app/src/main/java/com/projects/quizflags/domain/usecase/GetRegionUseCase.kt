package com.projects.quizflags.domain.usecase

import com.projects.quizflags.domain.model.Region
import com.projects.quizflags.domain.repository.CountryRepository
import javax.inject.Inject

class GetRegionUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Region>> {
        return repository.getRegions()
    }
}