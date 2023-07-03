package com.crewl.app.data.model.user

import com.crewl.app.data.model.country.Country

data class PhoneNumber(val country: Country, val number: String) {
    var withCountryCode: String = country.dialogCode + number
}