package com.smartherd.campushousing

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.smartherd.campushousing.data.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_payment)

        val amountText = findViewById<TextView>(R.id.paymentAmount)

        val cardNumber = findViewById<EditText>(R.id.cardNumber)

        val payBtn = findViewById<Button>(R.id.payBtn)

        val listingId = intent.getIntExtra("listingId", -1)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val listing = db.listingDao().getListingById(listingId)

            amountText.text = "Deposit Amount: BWP ${listing.depositAmount}"

            payBtn.setOnClickListener {

                if (cardNumber.text.toString().isEmpty()) {

                    Toast.makeText(
                        this@PaymentActivity,
                        "Enter card number",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                val reference =
                    "RES" + System.currentTimeMillis()

                val date =
                    SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(Date())

                lifecycleScope.launch {
                    val reservation = Reservation(
                        userId = 1,
                        listingId = listing.id,
                        referenceNumber = reference,
                        paymentAmount = listing.depositAmount,
                        reservationDate = date
                    )

                    db.reservationDao()
                        .insertReservation(reservation)

                    listing.isReserved = true

                    db.listingDao().updateListing(listing)

                    Toast.makeText(
                        this@PaymentActivity,
                        "Payment Successful\nReference: $reference",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
            }
        }
    }
}