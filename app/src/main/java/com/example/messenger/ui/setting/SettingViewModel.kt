package com.example.messenger.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.MessengerRepository
import com.example.messenger.model.User
import com.example.messenger.service.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState(currentUser = repository.googleService.getSignedInUser()))
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun updateTheme(change: Boolean) {
        val currentState = _uiState.value
        _uiState.value = SettingUiState(currentState.currentUser, onThemeChange = change)
    }

    suspend fun logout() {
        repository.googleService.signOut()
    }

    suspend fun deleteAccount() {
        repository.emailService.deleteAccount()
    }
}

data class SettingUiState(
    var currentUser: UserData? = null,
    var onThemeChange: Boolean = false
)