package com.example.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatRoom(
    val chatroomId: String? = null,
    val chatRoomName: String? = null,
    val chatRoomBackground: String? = null,
    val participants: List<User>? = null,
    val messages: List<Message>? = null,
    val createdAt: String? = null
)
