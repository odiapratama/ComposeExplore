package com.example.composeexplore.ui.view.dynamicisland


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White

@Composable
fun LeadingContent(state: IslandState) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxHeight(),
        visible = state.hasLeadingContent,
        enter = fadeIn(animationSpec = tween(300, 300))
    ) {
        Box(
            Modifier
                .width(state.leadingContentSize),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is IslandState.CallState -> {
                    Text(text = "21:00", color = White)
                }

                is IslandState.CallTimerState -> {
                    Icon(
                        imageVector = Icons.Rounded.Call,
                        contentDescription = null,
                        tint = Green,
                    )
                }

                else -> {}
            }
        }
    }
}