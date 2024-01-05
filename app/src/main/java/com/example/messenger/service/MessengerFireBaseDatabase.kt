package com.example.messenger.service

import android.util.Log
import com.example.messenger.data.local.MessengerDao
import com.example.messenger.model.ChatRoom
import com.example.messenger.model.Message
import com.example.messenger.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MessengerFireBaseDatabase(
    reference: DatabaseReference
) {
    private val messagesRef = reference.child(MESSAGES_REF_NAME)

    private val usersRef = reference.child(USER_REF_NAME)

    private val chatroomRef = reference.child(CHAT_ROOM_NAME)

    fun getListChatRoomsFromFirebase(callback: (List<ChatRoom>) -> Unit) {
        val chatRooms: MutableList<ChatRoom> = mutableListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val chatRoom = dataSnapshot.getValue<ChatRoom>()
                chatRoom?.let { chatRooms.add(it) }
                callback(chatRooms)
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                callback(emptyList())
            }
        }
        chatroomRef.addValueEventListener(postListener)
        Log.d(TAG, chatRooms.toString())
    }

    fun writeNewMessageIntoRealtimeDatabase(chatroomId: String, message: Message) {
        chatroomRef.child(chatroomId).child(MESSAGES_REF_NAME).child(message.messageId.toString())
            .setValue(message)
    }

    fun writeNewChatRoomIntoRealtimeDatabase() {
        val chatRoom = ChatRoom()
        chatroomRef.setValue(chatRoom)
    }

    companion object {
        const val MESSAGES_REF_NAME = "messages"
        const val USER_REF_NAME = "users"
        const val CHAT_ROOM_NAME = "chatrooms"
        const val TAG = "MessengerFireBaseDatabase"
    }
}