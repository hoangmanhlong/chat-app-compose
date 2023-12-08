package com.example.messenger.service

import com.example.messenger.model.User
import com.example.messenger.service.model.EmailService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class EmailServiceImpl : EmailService {
//    override val currentUser: Flow<User?>
//        get() = callbackFlow {
//            val listener = FirebaseAuth.AuthStateListener { auth ->
//                this.trySend(auth.currentUser?.let { User(id = it.uid, name = "Long") })
//            }
//            Firebase.auth.addAuthStateListener(listener)
//            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
//        }

    override val currentUserid: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean = Firebase.auth.currentUser != null

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun signOut() = Firebase.auth.signOut()

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }
}