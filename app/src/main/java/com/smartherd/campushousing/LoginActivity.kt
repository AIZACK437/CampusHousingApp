package com.smartherd.campushousing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.smartherd.campushousing.data.AppDatabase
import com.smartherd.campushousing.data.DatabaseSeeder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        db = AppDatabase.getDatabase(this)

        // SEED DATABASE

        DatabaseSeeder.seed(db)

        // VIEWS

        val emailEt =
            findViewById<EditText>(R.id.email)

        val passwordEt =
            findViewById<EditText>(R.id.password)

        val loginBtn =
            findViewById<Button>(R.id.loginBtn)

        val registerText =
            findViewById<TextView>(R.id.registerText)

        val userDao = db.userDao()

        // LOGIN BUTTON

        loginBtn.setOnClickListener {

            val email =
                emailEt.text.toString().trim()

            val password =
                passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                val user =
                    userDao.loginUser(email, password)

                runOnUiThread {

                    if (user != null) {

                        val sharedPreferences =
                            getSharedPreferences(
                                "CampusHousingPrefs",
                                MODE_PRIVATE
                            )

                        sharedPreferences.edit()
                            .putBoolean("isLoggedIn", true)
                            .apply()

                        startActivity(
                            Intent(
                                this@LoginActivity,
                                ListingsActivity::class.java
                            )
                        )

                        finish()

                    } else {

                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid login",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }

        // REGISTER NAVIGATION

        registerText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}