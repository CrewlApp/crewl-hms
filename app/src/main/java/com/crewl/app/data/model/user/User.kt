package com.crewl.app.data.model.user

import com.google.type.LatLng

data class User(
    val phoneNumber: PhoneNumber? = null,
    val name: String? = null,
    val surname: String? = null,
    val birthdate: String? = null,
    val coordinates: LatLng? = null,
    val profilePicture: String? = null
) {
    val fullName: String = "$name $surname"
}