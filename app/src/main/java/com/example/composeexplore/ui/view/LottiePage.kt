package com.example.composeexplore.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LottiePage() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://assets5.lottiefiles.com/packages/lf20_xlkigwme.json")
    )
    val lottieAnimation by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )

    LottieAnimation(
        composition = composition,
        progress = lottieAnimation,
        modifier = Modifier.padding(16.dp)
    )
}