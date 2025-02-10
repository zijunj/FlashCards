package com.example.flashcards

import android.content.res.XmlResourceParser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser

data class Flashcard(val question: String, val answer: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val flashcards = loadFlashcardsFromXml()

        setContent {
            FlashcardQuizApp(flashcards)
        }
    }

    private fun loadFlashcardsFromXml(): List<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()
        val parser: XmlResourceParser = resources.getXml(R.xml.flashcards)
        var question = ""
        var answer = ""

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "question" -> question = parser.nextText()
                        "answer" -> answer = parser.nextText()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "flashcard") {
                        flashcards.add(Flashcard(question, answer))
                    }
                }
            }
            parser.next()
        }
        return flashcards
    }
}