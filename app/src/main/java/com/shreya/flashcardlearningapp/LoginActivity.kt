package com.shreya.flashcardlearningapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var guestButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authManager: AuthManager // Instance of AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize FirebaseAuth and AuthManager
        mAuth = FirebaseAuth.getInstance()
        authManager = AuthManager(this)

        // Initialize Views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpButton)
        guestButton = findViewById(R.id.guestButton)

        // Set up button listeners
        loginButton.setOnClickListener { handleLogin() }
        signUpButton.setOnClickListener { navigateToSignUp() }
        guestButton.setOnClickListener { navigateAsGuest() }
    }

    private fun handleLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty()) {
            showToast("Please enter your email")
            return
        }
        if (password.isEmpty()) {
            showToast("Please enter your password")
            return
        }

        // Authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser?.let { user ->
                        showToast("Welcome ${user.email}")
                        onLoginSuccess(user.uid)
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Authentication failed"
                    showToast(errorMessage)
                }
            }
    }

    private fun navigateToSignUp() {
        // Navigate to SignUpActivity
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun navigateAsGuest() {
        Log.d("LoginActivity", "Navigating as Guest")
        // Navigate to HomeActivity as a guest
        startActivity(Intent(this, HomeActivity::class.java))
        finish() // Finish LoginActivity to prevent returning to login screen
    }

    private fun onLoginSuccess(token: String) {
        // Save session using AuthManager
        authManager.clearUserSession()
        authManager.saveUserSession(token)

        // Navigate to HomeActivity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Prevent navigating back to LoginActivity
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
