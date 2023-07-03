package com.crewl.app.data.model.user

data class User(
    val phoneNumber: PhoneNumber?,
    val name: String?,
    val surname: String?,
    val birthdate: String
) {
    var fullName: String = "$name $surname"
    var fullNumber: String = "${phoneNumber?.country?.code}${phoneNumber?.number}"
}