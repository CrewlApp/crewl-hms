package com.crewl.app.fixtures

import com.crewl.app.data.model.country.Country
import com.crewl.app.data.model.user.PhoneNumber
import com.crewl.app.framework.base.IOTaskResult

class TestFixtures {
    companion object {
        val MOCK_PHONE_NUMBER = PhoneNumber(Country(dialogCode = "+90"), "5324409818")
        val SUCCESS_RESULT = IOTaskResult.OnSuccess(true)
        val FIREBASE_CHECK_USER_FAILURE_RESULT = IOTaskResult.OnFailed(Exception("User not found"))
    }
}