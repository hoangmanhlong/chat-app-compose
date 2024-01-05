package com.example.messenger.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.model.Message
import com.example.messenger.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface MessengerDao {
//    @Query("SELECT * FROM message")
//    fun getMessages(): Flow<List<Message>>
//
//    @Query("SELECT * FROM user")
//    fun getUsers(): Flow<List<User>>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertMessages(messages: List<Message>)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertUsers(messages: List<User>)
}