package com.example.messenger.ui.sign_up_with_email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.messenger.data.MessengerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    suspend fun onSignUp(): Boolean {
        return repository.emailService.signUp(uiState.email, uiState.password)
    }

    fun updateUsername(string: String) {
        uiState = uiState.copy(email = string)
    }

    fun updatePassword(string: String) {
        uiState = uiState.copy(password = string)
    }

    fun updateConfirmPassword(string: String) {
        uiState = uiState.copy(confirmPassword = string)
    }

    fun clearText(type: Int) {
        uiState = when (type) {
            1 -> { uiState.copy(email = "") }
            2 -> uiState.copy(password = "")
            else -> uiState.copy(confirmPassword = "")
        }
    }

    fun check(): Boolean {
        return uiState.email.isNotBlank() &&
            uiState.password.isNotBlank() &&
            uiState.confirmPassword.isNotBlank() &&
            (uiState.password == uiState.confirmPassword)
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)