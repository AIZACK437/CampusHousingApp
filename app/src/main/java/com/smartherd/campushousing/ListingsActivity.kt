package com.smartherd.campushousing

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.view.Menu
import android.view.MenuItem
import android.content.SharedPreferences
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.campushousing.adapter.ListingAdapter
import com.smartherd.campushousing.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListingsActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var adapter: ListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_listings)

        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title =
            "Campus Housing"
        toolbar.setTitleTextColor(
            getColor(android.R.color.white)
        )
        toolbar.overflowIcon?.setTint(
            getColor(android.R.color.white)
        )

        db = AppDatabase.getDatabase(this)

        // RECYCLER VIEW

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerViewListings)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        adapter = ListingAdapter()

        recyclerView.adapter = adapter

        // FILTER UI

        val minPriceInput =
            findViewById<EditText>(R.id.minPriceInput)

        val maxPriceInput =
            findViewById<EditText>(R.id.maxPriceInput)

        val locationSpinner =
            findViewById<Spinner>(R.id.locationSpinner)

        val filterBtn =
            findViewById<Button>(R.id.filterBtn)

        // SPINNER DATA

        val locations = arrayOf(
            "All",
            "Gaboronoe West",
            "Broadhurst",
            "Tlokweng",
            "Mogoditshane",
            "Block 6"
        )

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            locations
        )

        locationSpinner.adapter = spinnerAdapter

        // LOAD ALL LISTINGS

        loadListings()

        // FILTER BUTTON

        filterBtn.setOnClickListener {

            val minPrice =
                minPriceInput.text.toString()
                    .toDoubleOrNull() ?: 0.0

            val maxPrice =
                maxPriceInput.text.toString()
                    .toDoubleOrNull() ?: 999999.0

            var location =
                locationSpinner.selectedItem.toString()

            if (location == "All") {

                location = "%"
            }

            lifecycleScope.launch(Dispatchers.IO) {

                val filteredListings =
                    db.listingDao().filterListings(
                        minPrice,
                        maxPrice,
                        location
                    )

                withContext(Dispatchers.Main) {

                    adapter.submitList(filteredListings)

                    // SMART ALERT NOTIFICATION

                    if (filteredListings.isNotEmpty()) {

                        showNotification(
                            "Listings Found",
                            "${filteredListings.size} matching rooms available"
                        )
                    }
                }
            }
        }
    }

    // LOAD ALL LISTINGS

    private fun loadListings() {

        lifecycleScope.launch(Dispatchers.IO) {

            val listings =
                db.listingDao().getAllListings()

            withContext(Dispatchers.Main) {

                adapter.submitList(listings)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.logoutMenu -> {

                val sharedPreferences =
                    getSharedPreferences(
                        "CampusHousingPrefs",
                        MODE_PRIVATE
                    )

                sharedPreferences.edit()
                    .putBoolean("isLoggedIn", false)
                    .apply()

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

                finish()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // NOTIFICATION FUNCTION

    private fun showNotification(
        title: String,
        message: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val channelId = "housing_channel"

        // CREATE CHANNEL

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Housing Alerts",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager =
                getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(channel)
        }

        // BUILD NOTIFICATION

        val builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this)
            .notify(1, builder.build())
    }
}