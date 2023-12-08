package com.example.messenger.model

data class Message(
    val messageId: String? = null,
    val sender: User? = null,
    val receiver: User? = null,
    val text: String? = null,
    val photoUrl: String? = null
)
