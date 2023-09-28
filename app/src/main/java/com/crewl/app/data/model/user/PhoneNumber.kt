package com.crewl.app.data.model.user

import android.os.Parcelable
import com.crewl.app.data.model.country.Country
import kotlinx.parcelize.Parcelize

data class PhoneNumber(val country: Country, val number: String) {
    val withCountryCode: String = country.dialogCode + number
}