package com.smartherd.campushousing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings")
data class Listing(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val price: Double,
    val location: String,
    val type: String,

    val amenities: String,
    val availabilityDate: String,

    val depositAmount: Double,

    val imageResId: Int,

    val status: String = "Available",

    var isReserved: Boolean = false
)