package com.smartherd.campushousing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}