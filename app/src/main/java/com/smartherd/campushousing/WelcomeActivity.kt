package com.smartherd.campushousing

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        val sharedPreferences =
            getSharedPreferences(
                "CampusHousingPrefs",
                MODE_PRIVATE
            )

        // SPLASH DELAY

        Handler(Looper.getMainLooper()).postDelayed({

            val isLoggedIn =
                sharedPreferences.getBoolean(
                    "isLoggedIn",
                    false
                )

            if (isLoggedIn) {

                // GO STRAIGHT TO LISTINGS

                startActivity(
                    Intent(
                        this,
                        ListingsActivity::class.java
                    )
                )

            } else {

                // GO TO LOGIN

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }

            finish()

        }, 2500)
    }
}