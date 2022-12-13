package com.example.composeexplore.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composeexplore.data.model.CardBack
import com.example.composeexplore.data.model.CardFace
import com.example.composeexplore.data.model.CardFront
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun CardFaceDisplay(
    cardFace: CardFace?,
    modifier: Modifier = Modifier
) {
    if (cardFace != null) {
        Box(contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(
                    16.dp,
                    MaterialTheme.colors.primary,
                    RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colors.surface)
        ) {
            when (cardFace) {
                is CardFront -> CardFrontContent(cardFace)
                is CardBack -> CardBackContent()
            }
        }
    } else {
        CardPlaceholderDisplay(modifier)
    }
}


@Composable
private fun CardFrontContent(
    cardFace: CardFront,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()
        .padding(paddingValues = PaddingValues(16.dp, 16.dp))) {
        Image(
            painter = rememberCoilPainter(cardFace.image),
            colorFilter = ColorFilter.tint(cardFace.cardColor),
            contentDescription = null,
            modifier = modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.6f)
                .align(Alignment.Center)
        )
        CardValue(cardFace = cardFace,
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .align(Alignment.TopStart)
        )
        CardValue(cardFace = cardFace,
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .rotate(180f)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun CardValue(
    cardFace: CardFront,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = cardFace.value,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = cardFace.cardColor
        )
        Image(
            painter = rememberCoilPainter(cardFace.image),
            colorFilter = ColorFilter.tint(cardFace.cardColor),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
private fun CardBackContent(
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
            .padding(16.dp)
    ) {
        Text(text = "Let's get flippin'",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary)
    }
}

@Composable
fun CardPlaceholderDisplay(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.surface)
    ) {
        Text(text = "Empty")
    }
}