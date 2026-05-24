package com.smartherd.campushousing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,

    val listingId: Int,

    val referenceNumber: String,

    val paymentAmount: Double,

    val reservationDate: String
)