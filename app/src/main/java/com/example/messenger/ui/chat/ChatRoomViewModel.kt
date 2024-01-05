package com.example.messenger.ui.chat

import androidx.lifecycle.ViewModel
import com.example.messenger.service.MessengerFireBaseDatabase
import com.example.messenger.service.MyOpenDocumentContract
import com.example.messenger.service.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val messengerFireBaseDatabase: MessengerFireBaseDatabase
) : ViewModel() {
    private val _uiState: MutableStateFlow<ChatRoomStatus> = MutableStateFlow(ChatRoomStatus())
    val uiState: StateFlow<ChatRoomStatus> = _uiState.asStateFlow()

    fun updateMessage(text: String) {
        _uiState.update { it.copy(message = text) }
    }

//    fun sendMessage() {
//        val value = _uiState.value
//        if(_uiState.value)
//        messengerFireBaseDatabase.writeNewMessageIntoRealtimeDatabase(
//            sender = value.currentUser.userId,
//            chatroomId = value.chatroomId,
//            text = value.message,
//            time = System.currentTimeMillis().toString()
//        )
//    }
}

data class ChatRoomStatus(
    val currentUser: UserData? = null,
    val chatroomId: String = "",
    val message: String = ""
)