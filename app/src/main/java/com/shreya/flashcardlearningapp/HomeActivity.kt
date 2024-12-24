package com.shreya.flashcardlearningapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var flashcardsButton: Button
    private lateinit var chatbotButton: Button
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize buttons
        flashcardsButton = findViewById(R.id.flashcardsButton)
        chatbotButton = findViewById(R.id.chatbotButton)  // Chatbot button initialized
        settingsButton = findViewById(R.id.settingsButton)

        // Set up onClick listeners for each button
        flashcardsButton.setOnClickListener {
            val intent = Intent(this, FlashcardsActivity::class.java)
            startActivity(intent)
        }


        chatbotButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)  // Intent for ChatActivity
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
