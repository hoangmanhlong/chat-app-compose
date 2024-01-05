package com.example.messenger.model


data class User(
    val userId: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val isOnline: Boolean? = null
)
