package com.crewl.app.domain.repository

import com.crewl.app.data.model.country.Country

interface LocalResourcesRepository {
    fun getCountries(): List<Country>
}