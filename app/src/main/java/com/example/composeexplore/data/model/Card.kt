package com.example.composeexplore.data.model

import androidx.compose.ui.graphics.Color

data class Card(
    val front: CardFace,
    val back: CardFace
)

sealed class CardFace

class CardFront(
    val image: String,
    val cardColor: Color,
    val value: String
) : CardFace()

object CardBack : CardFace()
