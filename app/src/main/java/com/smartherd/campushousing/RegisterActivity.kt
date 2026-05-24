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
import com.smartherd.campushousing.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        db = AppDatabase.getDatabase(this)

        val emailEt =
            findViewById<EditText>(R.id.registerEmail)

        val passwordEt =
            findViewById<EditText>(R.id.registerPassword)

        val registerBtn =
            findViewById<Button>(R.id.registerBtn)

        val loginText =
            findViewById<TextView>(R.id.loginText)

        registerBtn.setOnClickListener {

            val email =
                emailEt.text.toString().trim()

            val password =
                passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                db.userDao().registerUser(
                    User(
                        email = email,
                        password = password
                    )
                )

                runOnUiThread {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
            }
        }

        loginText.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}