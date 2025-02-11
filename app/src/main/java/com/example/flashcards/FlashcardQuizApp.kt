package com.example.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FlashcardQuizApp(initialFlashcards: List<Flashcard>) {
    var flashcards by remember { mutableStateOf(initialFlashcards) }

    // Auto-shuffle every 15 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(15000) // Wait 15 seconds
            flashcards = flashcards.shuffled() // Shuffle flashcards
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "History Flashcard Quiz",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(flashcards) { flashcard ->
                FlashcardView(flashcard)
            }
        }
    }
}

@Composable
fun FlashcardView(flashcard: Flashcard) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f, label = "")

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)), // Light Blue
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 250.dp, height = 150.dp)
            .clickable { isFlipped = !isFlipped }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density // Prevents distortion
                },
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // Show Question
                Text(
                    text = flashcard.question,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Show Answer, but flip it back to normal
                Text(
                    text = flashcard.answer,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer { rotationY = 180f } // Flip back to normal
                )
            }
        }
    }
}