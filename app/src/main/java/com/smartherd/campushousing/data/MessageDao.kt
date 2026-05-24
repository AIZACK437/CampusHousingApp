package com.smartherd.campushousing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Insert
    fun insertMessage(message: Message)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): List<Message>
}