package com.smartherd.campushousing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sender: String,
    val receiver: String,
    val message: String
)