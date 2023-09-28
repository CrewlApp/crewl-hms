/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.domain.repository

import com.crewl.app.data.repository.OnboardingRepositoryImpl
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    /**
     * @see OnboardingRepositoryImpl.dataStore
     */
    val dataStore: DataStore<Preferences>

    /**
     * @see OnboardingRepositoryImpl.readOnboardingState
     */
    fun readOnboardingState(): Flow<Boolean>

    /**
     * @see OnboardingRepositoryImpl.saveOnboardingState
     */
    suspend fun saveOnboardingState(isCompleted: Boolean)
}