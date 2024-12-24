package com.shreya.flashcardlearningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shreya.flashcardlearningapp.ui.theme.FlashcardLearningAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // If using edge-to-edge UI
        setContent {
            FlashcardLearningAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", // This will display "Hello Android"
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Hello $name!",
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { /* handle button click */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Click Me")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlashcardLearningAppTheme {
        Greeting("Android")
    }
}
