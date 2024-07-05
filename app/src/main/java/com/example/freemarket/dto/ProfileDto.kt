package com.example.freemarket.profiletest

<<<<<<< Updated upstream
data class Contacts(
    val id: String? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
    val imgUri: String? = null
)
=======
data class ProfileDto(
    var name: String? = null,
    val imgUri: String? = null,
    val phone: String? = null
) {
    constructor() : this("", "", "")
}
>>>>>>> Stashed changes
