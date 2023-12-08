package com.example.messenger.service.model

import com.example.messenger.model.User
import kotlinx.coroutines.flow.Flow

interface EmailService {
//    val currentUser: Flow<User?>
    val currentUserid: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signOut()
    suspend fun deleteAccount()
}