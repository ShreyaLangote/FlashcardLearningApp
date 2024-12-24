package com.shreya.flashcardlearningapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Create a list of URLs and button labels
        val links = mapOf(
            "Google" to "https://www.google.com",
            "Bing" to "https://www.bing.com",
            "DuckDuckGo" to "https://www.duckduckgo.com",
            "ChatGPT" to "https://www.chatgpt.com",
            "OpenAI" to "https://www.openai.com",
            "Reddit Ask Science" to "https://www.reddit.com/r/AskScience",
            "Stack Overflow" to "https://www.stackoverflow.com"
        )

        // Get the container LinearLayout for the buttons
        val buttonContainer = findViewById<LinearLayout>(R.id.buttonContainer)

        // Loop through the links and dynamically create buttons for each one
        links.forEach { (label, url) ->
            val button = Button(this).apply {
                text = label
                textSize = 18f
                setOnClickListener {
                    // Open the URL when the button is clicked
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
            // Add each button to the LinearLayout
            buttonContainer.addView(button)
        }

        // Back button logic
        val backButton = findViewById<Button>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish() // Close the ChatActivity and return to the previous screen
        }
    }
}
