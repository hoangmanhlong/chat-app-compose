package com.example.messenger.service.model

import android.content.Intent
import android.content.IntentSender

interface GoogleService {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?
}