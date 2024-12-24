package com.shreya.flashcardlearningapp

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val USER_TOKEN_KEY = "user_token"
    }

    /**
     * Save session token (user UID).
     */
    fun saveUserSession(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN_KEY, token)
        editor.apply() // Apply changes asynchronously
    }

    /**
     * Get session token.
     * @return The saved session token or null if not available.
     */
    fun getUserSession(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    /**
     * Clear session token.
     */
    fun clearUserSession() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_TOKEN_KEY)
        editor.apply() // Apply changes asynchronously
    }

    /**
     * Check if the user session exists (user is logged in).
     * @return True if the user is logged in, false otherwise.
     */
    fun isUserLoggedIn(): Boolean {
        return !getUserSession().isNullOrEmpty()
    }
}
