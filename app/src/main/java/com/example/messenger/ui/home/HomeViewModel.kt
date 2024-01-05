package com.example.messenger.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.MessengerRepository
import com.example.messenger.model.ChatRoom
import com.example.messenger.service.model.SignInResult
import com.example.messenger.service.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            HomeUiState(
                currentUser = repository.googleService.getSignedInUser()
            )
        )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        repository.messengerFireBaseDatabase.getListChatRoomsFromFirebase { chatrooms ->
            _uiState.update { it.copy(chatRooms = chatrooms) }
        }
    }

    suspend fun signIn(intent: Intent): SignInResult {
        return repository.googleService.signInWithIntent(intent)
    }


    fun onSignInResult(result: SignInResult) {
        _uiState.update {
            it.copy(
                currentUser = result.data,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() = _uiState.update { HomeUiState() }
}

data class HomeUiState(
    val chatRooms: List<ChatRoom> = emptyList(),
    var currentUser: UserData? = null,
    val signInError: String? = null
)

@Suppress("DEPRECATION")
private fun checkIfOnline(context: Context): Boolean {
    val cm = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } else {
        cm?.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}