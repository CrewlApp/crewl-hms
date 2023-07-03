package com.crewl.app.domain.repository

import com.crewl.app.data.model.country.Country
import com.crewl.app.data.model.user.PhoneNumber
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun fetchCountries(): List<Country>

    suspend fun searchCountry(query: String): Flow<List<Country>>
}