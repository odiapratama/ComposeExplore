package com.example.composeexplore.utils

import androidx.compose.ui.graphics.Color
import com.example.composeexplore.data.model.Card
import com.example.composeexplore.data.model.CardBack
import com.example.composeexplore.data.model.CardFront
import com.example.composeexplore.data.model.Email

val deck = listOf(
    Card(
        front = CardFront(
            "https://www.vecteezy.com/vector-art/580757-love-logo-and-symbols-vector-template",
            Color.Gray,
            "1"
        ),
        back = CardBack
    ),
    Card(
        front = CardFront(
            "https://www.vecteezy.com/vector-art/580757-love-logo-and-symbols-vector-template",
            Color.Blue,
            "2"
        ),
        back = CardBack
    ),
    Card(
        front = CardFront(
            "https://www.vecteezy.com/vector-art/580757-love-logo-and-symbols-vector-template",
            Color.Red,
            "3"
        ),
        back = CardBack
    ),
    Card(
        front = CardFront(
            "https://www.vecteezy.com/vector-art/580757-love-logo-and-symbols-vector-template",
            Color.DarkGray,
            "4"
        ),
        back = CardBack
    ),
    Card(
        front = CardFront(
            "https://www.vecteezy.com/vector-art/580757-love-logo-and-symbols-vector-template",
            Color.Yellow,
            "5"
        ),
        back = CardBack
    )
)

const val videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"

val emailSamples = mutableListOf<Email>().apply {
    repeat(10) {
        add(
            Email(
                subject = "Subject $it",
                message = "Message $it",
                dateTime = "${it + 1}:00"
            )
        )
    }
}.toList()