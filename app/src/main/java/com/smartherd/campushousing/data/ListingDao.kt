package com.smartherd.campushousing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListingDao {

    @Insert
    suspend fun insertListing(listing: Listing)

    @Query("SELECT * FROM listings")
    suspend fun getAllListings(): List<Listing>

    @Query("SELECT * FROM listings WHERE status = 'Available'")
    suspend fun getAvailableListings(): List<Listing>

    @Update
    suspend fun updateListing(listing: Listing)

    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getListingById(id: Int): Listing

    @Query("""
    SELECT * FROM listings
    WHERE price BETWEEN :minPrice AND :maxPrice
    AND location LIKE :location
    """)
    fun filterListings(
        minPrice: Double,
        maxPrice: Double,
        location: String
    ): List<Listing>
}
