package com.projects.quizflags.di

import com.projects.quizflags.data.datasource.CountriesDataSource
import com.projects.quizflags.data.datasource.CountriesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindCountriesDataSource(
        countriesDataSourceImpl: CountriesDataSourceImpl
    ): CountriesDataSource
}