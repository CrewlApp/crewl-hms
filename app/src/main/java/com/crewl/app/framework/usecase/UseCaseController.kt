package com.alis.framework.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCaseController<in Params, ReturnType> where ReturnType : Any {
    protected abstract suspend fun execute(params: Params): Flow<ReturnType>

    suspend operator fun invoke(params: Params): Flow<ReturnType> = execute(params = params).flowOn(context = Dispatchers.IO)
}