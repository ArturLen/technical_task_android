package com.example.sliideusersbook.userslist.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val gender: UserGender = UserGender.MALE,
    val status: UserStatus = UserStatus.ACTIVE
)