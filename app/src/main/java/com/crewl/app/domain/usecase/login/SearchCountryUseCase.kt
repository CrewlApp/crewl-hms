package com.crewl.app.domain.usecase.login

import androidx.annotation.VisibleForTesting
import com.crewl.app.data.model.country.Country
import com.crewl.app.domain.repository.LoginRepository
import com.crewl.app.framework.network.DataState
import com.crewl.app.framework.usecase.DataStateUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class SearchCountryUseCase @Inject constructor(@get: VisibleForTesting(otherwise = VisibleForTesting.PROTECTED) internal val repository: LoginRepository) :
    DataStateUseCase<SearchCountryUseCase.Params, List<Country>>() {
    data class Params(
        val query: String? = null
    )

    override suspend fun FlowCollector<DataState<List<Country>>>.execute(params: Params) {
        params.query?.let { query ->
            repository.searchCountry(query = query)
        }
    }
}