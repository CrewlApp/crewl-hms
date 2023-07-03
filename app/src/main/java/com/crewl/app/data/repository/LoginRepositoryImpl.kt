package com.crewl.app.data.repository

import com.crewl.app.data.model.country.Country
import com.crewl.app.data.source.local.CountryDataSource
import com.crewl.app.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val countryDataSource: CountryDataSource
): LoginRepository {
    override suspend fun fetchCountries(): List<Country> = countryDataSource.fetch()

    override suspend fun searchCountry(query: String): Flow<List<Country>> = flow {
        val countries = mutableListOf<Country>()

        countryDataSource.fetch().forEach { country: Country ->
                if (country.name.lowercase().contains(query.lowercase()) || country.dialogCode.contains(query.lowercase()))
                    countries.add(country)
        }

        emit(countries)
    }
}