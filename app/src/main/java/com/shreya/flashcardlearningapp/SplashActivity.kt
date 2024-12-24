package com.shreya.flashcardlearningapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize AuthManager to check if the user is logged in
        authManager = AuthManager(this)

        // Find the ImageView and apply fade-in animation to the logo
        val logo = findViewById<ImageView>(R.id.splashLogo)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)

        // Set up a delay for transitioning to the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            if (authManager.isUserLoggedIn()) {
                // If the user is logged in, navigate to HomeActivity
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // If the user is not logged in, navigate to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            // Close the SplashActivity so it doesn't appear in the back stack
            finish()
        }, 3000) // 3 seconds delay
    }
}
