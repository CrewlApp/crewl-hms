package com.crewl.app.domain.usecase.authentication

import com.crewl.app.data.repository.AuthenticationRepositoryImpl
import com.crewl.app.fixtures.TestFixtures
import com.crewl.app.framework.base.IOTaskResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CheckUserUseCaseTest {
    private lateinit var checkUserUseCase: CheckUserUseCase

    @Mock
    private lateinit var authenticationRepositoryImpl: AuthenticationRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        checkUserUseCase = CheckUserUseCase(repository = authenticationRepositoryImpl)
    }

    @Test
    fun `isUserExists should return success result when user exists`() = runBlocking {
        whenever(
            authenticationRepositoryImpl
                .isUserExists(TestFixtures.MOCK_PHONE_NUMBER.withCountryCode)
        )
            .thenReturn(flowOf(TestFixtures.SUCCESS_RESULT))

        val result = mutableListOf<IOTaskResult<Boolean>>()
        checkUserUseCase.invoke(CheckUserUseCase.Params(TestFixtures.MOCK_PHONE_NUMBER)).collect {
            result.add(it)
        }

        assertEquals(listOf(TestFixtures.SUCCESS_RESULT), result)
    }

    @Test
    fun `given user does not exist, when execute is called, then return OnFailed with error`(): Unit = runBlocking {
        Mockito.`when`(authenticationRepositoryImpl.isUserExists(TestFixtures.MOCK_PHONE_NUMBER.withCountryCode)).thenReturn(
            flow { emit(TestFixtures.FIREBASE_CHECK_USER_FAILURE_RESULT) }
        )

        checkUserUseCase.invoke(CheckUserUseCase.Params(TestFixtures.MOCK_PHONE_NUMBER))
            .catch {
                throw Exception(it.message)
            }
            .collect {
                assert(it is IOTaskResult.OnFailed)
            }
    }
}