/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.domain.usecase.onboarding

import androidx.annotation.VisibleForTesting
import com.crewl.app.framework.usecase.LocalUseCaseController
import com.crewl.app.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SaveOnboardingUseCase @Inject constructor
    (@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED) internal val repository: OnboardingRepository) :
    LocalUseCaseController<SaveOnboardingUseCase.Params, Unit>() {
    data class Params(
        val isCompleted: Boolean
    )

    override suspend fun FlowCollector<Unit>.execute(params: Params) {
        repository.saveOnboardingState(params.isCompleted)
        emit(Unit)
    }
}