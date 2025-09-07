package com.projects.quizflags.di

import com.projects.quizflags.data.repository.CountryRepositoryImpl
import com.projects.quizflags.data.repository.GameRepositoryImpl
import com.projects.quizflags.domain.repository.CountryRepository
import com.projects.quizflags.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCountryRepository(
        countryRepositoryImpl: CountryRepositoryImpl
    ): CountryRepository

    @Binds
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): GameRepository
}