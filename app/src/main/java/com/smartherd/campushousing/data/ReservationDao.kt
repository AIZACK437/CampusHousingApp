package com.smartherd.campushousing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {

    @Insert
    suspend fun insertReservation(reservation: Reservation)

    @Query("SELECT * FROM reservations")
    suspend fun getAllReservations(): List<Reservation>
}