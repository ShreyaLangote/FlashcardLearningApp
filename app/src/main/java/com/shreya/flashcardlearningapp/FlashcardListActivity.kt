package com.shreya.flashcardlearningapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FlashcardListActivity : AppCompatActivity() {

    private lateinit var flashcardsList: MutableList<Flashcard>
    private lateinit var sharedPreferences: SharedPreferences
    private val flashcardsKey = "flashcards_list"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_list)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        loadFlashcards()

        val adapter = FlashcardAdapter(flashcardsList) { position ->
            deleteFlashcard(position)  // Handle flashcard deletion
        }
        recyclerView.adapter = adapter
    }

    private fun loadFlashcards() {
        val gson = Gson()
        val json = sharedPreferences.getString(flashcardsKey, null)

        if (json != null) {
            val type = object : TypeToken<MutableList<Flashcard>>() {}.type
            flashcardsList = gson.fromJson(json, type)
        } else {
            flashcardsList = mutableListOf()
        }
    }

    private fun deleteFlashcard(position: Int) {
        flashcardsList.removeAt(position)
        saveFlashcards()  // Save the updated list
        Toast.makeText(this, "Flashcard deleted", Toast.LENGTH_SHORT).show()
    }

    private fun saveFlashcards() {
        val gson = Gson()
        val json = gson.toJson(flashcardsList)
        val editor = sharedPreferences.edit()
        editor.putString(flashcardsKey, json)
        editor.apply()
    }
}
