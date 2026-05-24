package com.smartherd.campushousing

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.smartherd.campushousing.data.AppDatabase
import kotlinx.coroutines.launch

class ListingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listings_details)

        val image = findViewById<ImageView>(R.id.detailImage)
        val title = findViewById<TextView>(R.id.detailTitle)
        val price = findViewById<TextView>(R.id.detailPrice)
        val location = findViewById<TextView>(R.id.detailLocation)
        val type = findViewById<TextView>(R.id.detailType)
        val amenities = findViewById<TextView>(R.id.detailAmenities)
        val availability = findViewById<TextView>(R.id.detailAvailability)
        val deposit = findViewById<TextView>(R.id.detailDeposit)
        val status = findViewById<TextView>(R.id.detailStatus)
        val chatBtn = findViewById<Button>(R.id.chatBtn)

        val reserveBtn = findViewById<Button>(R.id.btnReserve)

        val listingId = intent.getIntExtra("listingId", -1)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val listing = db.listingDao().getListingById(listingId)

            image.setImageResource(listing.imageResId)

            title.text = listing.title
            price.text = "Price: BWP ${listing.price}"
            location.text = "Location: ${listing.location}"
            type.text = "Type: ${listing.type}"
            amenities.text = "Amenities: ${listing.amenities}"
            availability.text = "Available: ${listing.availabilityDate}"
            deposit.text = "Deposit: BWP ${listing.depositAmount}"

            if (listing.isReserved) {

                status.text = "STATUS: RESERVED"

                reserveBtn.isEnabled = false

            } else {

                status.text = "STATUS: AVAILABLE"
            }

            reserveBtn.setOnClickListener {

                val intent = Intent(this@ListingDetailsActivity, PaymentActivity::class.java)

                intent.putExtra("listingId", listing.id)

                startActivity(intent)
            }

            chatBtn.setOnClickListener {

                startActivity(
                    Intent(this@ListingDetailsActivity, ChatActivity::class.java)
                )
            }
        }
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        supportActionBar?.title =
            "Campus Housing"

        toolbar.setTitleTextColor(
            getColor(android.R.color.white)
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(
            getColor(android.R.color.white)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}