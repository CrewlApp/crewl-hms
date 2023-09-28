package com.crewl.app.utils

import com.crewl.app.data.model.user.PhoneNumber

class RegexValidation {
    companion object {
        @JvmStatic
        fun checkPhoneNumber(phoneNumber: String): Boolean =
            phoneNumber.isNotEmpty() && android.util.Patterns.PHONE.matcher(phoneNumber).matches()
    }
}

val PhoneNumber.isValid: Boolean
    get() = this.number.isNotEmpty() && android.util.Patterns.PHONE.matcher(this.number).matches()