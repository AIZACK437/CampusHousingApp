package com.smartherd.campushousing

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.smartherd.campushousing.data.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat)

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

        val chatMessages =
            findViewById<TextView>(R.id.chatMessages)

        val messageInput =
            findViewById<EditText>(R.id.messageInput)

        val sendBtn =
            findViewById<Button>(R.id.sendBtn)

        val db = AppDatabase.getDatabase(this)

        loadMessages(chatMessages, db)

        sendBtn.setOnClickListener {

            val text =
                messageInput.text.toString()

            if (text.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter message",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val message = Message(
                sender = "Student",
                receiver = "Landlord",
                message = text
            )

            db.messageDao().insertMessage(message)

            messageInput.setText("")

            loadMessages(chatMessages, db)
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }

    private fun loadMessages(
        textView: TextView,
        db: AppDatabase
    ) {

        val messages =
            db.messageDao().getAllMessages()

        var content = ""

        for (msg in messages) {

            if (msg.sender == "Student") {

                content +=
                    "\nYou:\n${msg.message}\n\n"

            } else {

                content +=
                    "Landlord:\n${msg.message}\n\n"
            }
        }

        textView.text = content
    }
}