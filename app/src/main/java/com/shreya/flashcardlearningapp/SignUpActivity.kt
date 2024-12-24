package com.shreya.flashcardlearningapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity : AppCompatActivity() {

    // Declare FirebaseAuth instance
    private lateinit var mAuth: FirebaseAuth

    // Declare other UI components
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance()

        // Initialize UI components
        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        signUpButton = findViewById(R.id.signUpButton)

        // Set up sign-up button click listener
        signUpButton.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            // Validate that both email and password are not empty
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                // Create user with Firebase Authentication
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this@SignUpActivity) { task ->
                        if (task.isSuccessful) {
                            // Successfully signed up
                            val user = mAuth.currentUser
                            Toast.makeText(this@SignUpActivity, "Sign-up successful", Toast.LENGTH_SHORT).show()

                            // Go to HomeActivity after sign-up
                            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                            finish()  // Close SignUpActivity to prevent going back
                        } else {
                            // Sign-up failed
                            Toast.makeText(this@SignUpActivity, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

