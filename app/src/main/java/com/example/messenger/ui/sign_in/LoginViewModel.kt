package com.example.messenger.ui.sign_in

import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.messenger.data.MessengerRepository
import com.example.messenger.service.GoogleServiceImpl
import com.example.messenger.service.model.SignInResult
import com.example.messenger.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return repository.googleService.signInWithIntent(intent)
    }

    suspend fun signIn(): IntentSender? {
        return repository.googleService.signIn()
    }

    fun onSignInResult(result: SignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }
}

data class LoginUiState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)