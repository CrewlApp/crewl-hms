package com.crewl.app.data.repository

import com.crewl.app.data.model.country.Country
import com.crewl.app.data.source.local.CountryDataSource
import com.crewl.app.domain.repository.LocalResourcesRepository
import javax.inject.Inject

class LocalResourcesRepositoryImpl @Inject constructor(
    private val countryDataSource: CountryDataSource
): LocalResourcesRepository {
    override fun getCountries(): List<Country> = countryDataSource.fetch()
}