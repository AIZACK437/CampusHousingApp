package com.smartherd.campushousing.data

import com.smartherd.campushousing.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {

    fun seed(database: AppDatabase) {

        val userDao = database.userDao()

        val listingDao = database.listingDao()

        CoroutineScope(Dispatchers.IO).launch {

            if (userDao.getAllUsers().isNotEmpty()) {
                return@launch
            }

            val users = listOf(

                User(
                    email = "isaac@student.com",
                    password = "1234"
                ),

                User(
                    email = "neo@student.com",
                    password = "1234"
                ),

                User(
                    email = "sarah@student.com",
                    password = "1234"
                ),

                User(
                    email = "dube@landlord.com",
                    password = "1234"
                ),

                User(
                    email = "mpho@landlord.com",
                    password = "1234"
                )
            )

            users.forEach {

                userDao.registerUser(it)
            }

            // =========================
            // LISTINGS
            // =========================

            val listings = listOf(

                Listing(
                    title = "Modern Student Apartment",
                    price = 2500.0,
                    location = "Gaborone West",
                    type = "Single Room",
                    amenities = "WiFi, Water, Electricity",
                    availabilityDate = "2026-05-10",
                    depositAmount = 1200.0,
                    imageResId = R.drawable.house1,
                    status = "Available"
                ),

                Listing(
                    title = "Affordable Shared Room",
                    price = 1500.0,
                    location = "Tlokweng",
                    type = "Shared Room",
                    amenities = "WiFi, Parking",
                    availabilityDate = "2026-05-14",
                    depositAmount = 700.0,
                    imageResId = R.drawable.house2,
                    status = "Available"
                ),

                Listing(
                    title = "Luxury Studio Near UB",
                    price = 4200.0,
                    location = "Broadhurst",
                    type = "Studio",
                    amenities = "WiFi, Security, Furnished",
                    availabilityDate = "2026-05-20",
                    depositAmount = 2000.0,
                    imageResId = R.drawable.house3,
                    status = "Available"
                ),

                Listing(
                    title = "Quiet Bachelor Pad",
                    price = 3200.0,
                    location = "Block 6",
                    type = "Bachelor",
                    amenities = "Water Included, Parking",
                    availabilityDate = "2026-06-01",
                    depositAmount = 1500.0,
                    imageResId = R.drawable.house4,
                    status = "Available"
                ),

                Listing(
                    title = "Student Room Near Limkokwing",
                    price = 1800.0,
                    location = "Mogoditshane",
                    type = "Single Room",
                    amenities = "WiFi, Electricity",
                    availabilityDate = "2026-05-17",
                    depositAmount = 900.0,
                    imageResId = R.drawable.house5,
                    status = "Available"
                )
            )

            listings.forEach {

                listingDao.insertListing(it)
            }

            // =========================
            // AUTO-GENERATE EXTRA DATA
            // =========================

            val locations = listOf(
                "Gaborone West",
                "Broadhurst",
                "Tlokweng",
                "Mogoditshane",
                "Block 6"
            )

            val roomTypes = listOf(
                "Single Room",
                "Shared Room",
                "Studio",
                "Bachelor"
            )

            val amenitiesList = listOf(
                "WiFi, Water, Electricity",
                "Parking, Security",
                "WiFi, Furnished",
                "Electricity Included",
                "Water Included, Parking"
            )

            for (i in 6..50) {

                val randomPrice =
                    (1200..4500).random().toDouble()

                val randomDeposit =
                    (500..2000).random().toDouble()

                val randomLocation =
                    locations.random()

                val randomType =
                    roomTypes.random()

                val randomAmenities =
                    amenitiesList.random()

                val randomDate =
                    "2026-06-${(10..28).random()}"

                listingDao.insertListing(

                    Listing(
                        title = "Student Housing $i",
                        price = randomPrice,
                        location = randomLocation,
                        type = randomType,
                        amenities = randomAmenities,
                        availabilityDate = randomDate,
                        depositAmount = randomDeposit,
                        imageResId = R.drawable.house1,
                        status = "Available"
                    )
                )
            }
        }
    }
}