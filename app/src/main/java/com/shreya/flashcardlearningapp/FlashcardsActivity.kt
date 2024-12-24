package com.shreya.flashcardlearningapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FlashcardsActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var flashcardsList: MutableList<Flashcard>
    private val flashcardsKey = "flashcards_list"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FlashcardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards)

        // Initialize views
        questionEditText = findViewById(R.id.questionEditText)
        answerEditText = findViewById(R.id.answerEditText)
        saveButton = findViewById(R.id.saveButton)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        // Load flashcards from SharedPreferences
        loadFlashcards()

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FlashcardAdapter(flashcardsList) { position ->
            deleteFlashcard(position)
        }
        recyclerView.adapter = adapter

        // Enable Swipe to Delete functionality
        adapter.enableSwipeToDelete(recyclerView)

        // Set up Save button click listener
        saveButton.setOnClickListener {
            saveFlashcard()
        }
    }

    // Function to save the flashcard entered by the user
    private fun saveFlashcard() {
        val question = questionEditText.text.toString()
        val answer = answerEditText.text.toString()

        if (question.isNotEmpty() && answer.isNotEmpty()) {
            val newFlashcard = Flashcard(question, answer)
            flashcardsList.add(newFlashcard)  // Add new flashcard to the list
            saveFlashcards()  // Save the updated list to SharedPreferences
            adapter.notifyItemInserted(flashcardsList.size - 1)  // Notify the RecyclerView that a new item has been added

            // Show confirmation message
            Toast.makeText(this, "Flashcard saved!", Toast.LENGTH_SHORT).show()

            // Clear the input fields
            questionEditText.text.clear()
            answerEditText.text.clear()
        } else {
            // Show an error if fields are empty
            Toast.makeText(this, "Please fill out both question and answer", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to save the list of flashcards to SharedPreferences
    private fun saveFlashcards() {
        val gson = Gson()
        val json = gson.toJson(flashcardsList)  // Convert the list to JSON
        val editor = sharedPreferences.edit()
        editor.putString(flashcardsKey, json)  // Save the JSON string to SharedPreferences
        editor.apply()  // Commit the changes
    }

    // Function to load flashcards from SharedPreferences
    private fun loadFlashcards() {
        val gson = Gson()
        val json = sharedPreferences.getString(flashcardsKey, null)

        if (json != null) {
            val type = object : TypeToken<MutableList<Flashcard>>() {}.type
            flashcardsList = gson.fromJson(json, type)  // Deserialize the JSON back into a list
        } else {
            flashcardsList = mutableListOf()  // If no data, initialize an empty list
        }
    }

    // Function to delete a flashcard from the list
    private fun deleteFlashcard(position: Int) {
        flashcardsList.removeAt(position)
        saveFlashcards()  // Save the updated list
        adapter.notifyItemRemoved(position)  // Notify the adapter about the removed item
    }
}
