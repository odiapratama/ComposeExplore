package com.example.composeexplore.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    progress: Float,
    onClick: () -> Unit
) {
    var height by remember { mutableStateOf(0.dp) }
    val shape = RoundedCornerShape(8.dp)
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = shape
            )
            .clip(shape = shape)
    ) {
        Button(
            modifier = modifier
                .onSizeChanged {
                    height = density.run { it.height.toDp() }
                },
            onClick = onClick,
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Click", color = Color.White)
        }
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(height),
            progress = progress,
            backgroundColor = Color.Transparent,
            color = Color.White.copy(alpha = 0.15f)
        )
    }
}