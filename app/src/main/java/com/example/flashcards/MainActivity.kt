package com.example.flashcards

import android.content.res.XmlResourceParser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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