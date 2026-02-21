package com.example.testproject.app.data.database.model

data class UserDbModel(
    var id: String? = "",
    var name: String? = "",
    var lastName: String? = "",
    var gender: Boolean? = false,
    var dateOfBirth: String? = "",
    var height: String? = "",
    var weight: String? = "",
    var targetWeight: String? = "",
    var email: String? = "",
    //var password: String? = "",
    var country: String? = "",
    var city: String? = ""
)

