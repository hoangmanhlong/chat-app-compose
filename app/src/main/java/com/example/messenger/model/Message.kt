package com.example.messenger.model


data class Message(
    val messageId: String? = null,
    val sender: String? = null,
    val receiver: String? = null,
    val text: String? = null,
    val photoUrl: String? = null,
    val time: String? = null
)
