package com.shreya.flashcardlearningapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var signOutButton: Button
    private lateinit var notificationSwitch: Switch
    private lateinit var mAuth: FirebaseAuth
    private lateinit var lightTheme: RadioButton
    private lateinit var darkTheme: RadioButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authManager: AuthManager // Initialize AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences first
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        // Initialize AuthManager
        authManager = AuthManager(this)

        // Load the theme before setting the content view
        loadTheme()

        setContentView(R.layout.activity_settings)

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Initialize UI components
        signOutButton = findViewById(R.id.signOutButton)
        notificationSwitch = findViewById(R.id.notificationSwitch)
        lightTheme = findViewById(R.id.lightTheme)
        darkTheme = findViewById(R.id.darkTheme)

        // Set initial state of RadioButtons based on saved theme
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            lightTheme.isChecked = true
            darkTheme.isChecked = false
        } else {
            lightTheme.isChecked = false
            darkTheme.isChecked = true
        }

        // Handle Sign Out button click
        signOutButton.setOnClickListener {
            handleSignOut()
        }

        // Handle Notification Switch
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Enable or disable notifications
            // (Implement notification logic here)
        }

        // Handle Theme selection
        lightTheme.setOnClickListener {
            // Apply Light Theme
            setThemePreference(R.style.Theme_FlashcardLearningApp_Light)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            restartActivity()
        }

        darkTheme.setOnClickListener {
            // Apply Dark Theme
            setThemePreference(R.style.Theme_FlashcardLearningApp_Dark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            restartActivity()
        }
    }

    private fun handleSignOut() {
        // Sign out from Firebase
        mAuth.signOut()

        // Clear local data (SharedPreferences, user-related data, etc.)
        authManager.clearUserSession() // Now this works since authManager is initialized
        clearLocalData() // Clear other local data if necessary

        // Redirect to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // To prevent returning back to SettingsActivity
    }

    private fun clearLocalData() {
        // Clear SharedPreferences
        val editor = sharedPreferences.edit()
        editor.clear() // This clears all saved preferences
        editor.apply()

        // You can also clear any other stored data like local databases or cached files
        // For example:
        // clearDatabase() or clearCachedFiles()
    }

    // Save theme preference
    private fun setThemePreference(theme: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("selected_theme", theme)
        editor.apply()
    }

    // Load saved theme preference
    private fun loadTheme() {
        val savedTheme = sharedPreferences.getInt("selected_theme", R.style.Theme_FlashcardLearningApp_Light)

        // Set the theme based on saved preference
        when (savedTheme) {
            R.style.Theme_FlashcardLearningApp_Light -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            R.style.Theme_FlashcardLearningApp_Dark -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun restartActivity() {
        // Restart the current activity to apply the new theme
        val intent = intent
        finish()
        startActivity(intent)
    }
}
