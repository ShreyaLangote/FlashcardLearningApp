package com.shreya.flashcardlearningapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddFlashcardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flashcard)

        // Initialize Category Spinner
        val spinnerCategories = findViewById<Spinner>(R.id.spinnerFlashcardCategories)
        val categories = listOf("General", "Math", "Science", "History", "Technology", "Geography")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerCategories.adapter = categoryAdapter

        // Initialize Subject Spinner
        val spinnerSubjects = findViewById<Spinner>(R.id.spinnerFlashcardSubjects)
        val subjects = listOf("Math", "Science", "History", "Geography", "Technology")
        val subjectAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subjects)
        spinnerSubjects.adapter = subjectAdapter

        // Initialize Difficulty Level RadioGroup
        val radioGroupDifficulty = findViewById<RadioGroup>(R.id.radioGroupFlashcardDifficulty)

        // Get the Flashcard fields
        val editTextTitle = findViewById<EditText>(R.id.editTextFlashcardTitle)
        val editTextContent = findViewById<EditText>(R.id.editTextFlashcardContent)

        // Save Flashcard Button
        val buttonSaveFlashcard = findViewById<Button>(R.id.buttonSaveFlashcard)
        buttonSaveFlashcard.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()

            // Get the selected difficulty level
            val selectedDifficulty = when (radioGroupDifficulty.checkedRadioButtonId) {
                R.id.radioEasyFlashcard -> "Easy"
                R.id.radioModerateFlashcard -> "Moderate"
                R.id.radioDifficultFlashcard -> "Difficult"
                else -> ""
            }

            // Get the selected category and subject
            val selectedCategory = spinnerCategories.selectedItem.toString()
            val selectedSubject = spinnerSubjects.selectedItem.toString()

            if (title.isNotEmpty() && content.isNotEmpty() && selectedDifficulty.isNotEmpty()) {
                // Save the flashcard under selected category and subject
                val flashcard = Flashcard(
                    question = title,  // 'title' corresponds to the question
                    answer = content,  // 'content' corresponds to the answer

                )

                // You would save the flashcard in your database or local storage here.
                // For now, just show a toast message
                Toast.makeText(this, "Flashcard saved: $selectedDifficulty - $selectedCategory - $selectedSubject", Toast.LENGTH_SHORT).show()

                // Clear the fields after saving
                editTextTitle.text.clear()
                editTextContent.text.clear()
                radioGroupDifficulty.clearCheck()
                spinnerCategories.setSelection(0)
                spinnerSubjects.setSelection(0)

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
