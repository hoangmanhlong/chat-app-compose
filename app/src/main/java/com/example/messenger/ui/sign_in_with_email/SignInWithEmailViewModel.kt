package com.example.messenger.ui.sign_in_with_email

import androidx.lifecycle.ViewModel
import com.example.messenger.data.MessengerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInWithEmailViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInWithEmailUiState())
    val uiState: StateFlow<SignInWithEmailUiState> = _uiState.asStateFlow()

    suspend fun onLogin(): Boolean {
        return repository.emailService.signIn(uiState.value.email, uiState.value.password)
    }

    fun updateUsername(string: String) {
        _uiState.update {
            it.copy(email = string)
        }
    }

    fun updatePassword(string: String) {
        _uiState.update {
            it.copy(password = string)
        }
    }

    fun inputIsTrue(): Boolean =
        _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank()

    fun clearText(type: Int) {
        _uiState.update {
            if (type == 1) it.copy(email = "")
            else it.copy(password = "")
        }
    }
}

data class SignInWithEmailUiState(
    val email: String = "",
    val password: String = ""
)