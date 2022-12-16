package com.example.composeexplore.ui.component.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


inline fun Modifier.clickableNoRipple(
    crossinline onClick: () -> Unit
) : Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = {
            onClick()
        }
    )
}

@Composable
fun animateRotation() : Float {
    val transition = rememberInfiniteTransition()
    val animation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing)
        )
    )

    return animation
}

@Composable
fun scaleAnimation(
    buttonState: ButtonState,
    isSelected: Boolean,
    buttonText: String
): Triple<Animatable<Float, AnimationVector1D>, Animatable<Float, AnimationVector1D>, String> {
    val scaleText = remember { Animatable(initialValue = 1f) }
    val scaleIcon = remember { Animatable(initialValue = 0f) }
    var displayText by remember { mutableStateOf(buttonText) }

    LaunchedEffect(key1 = isSelected) {
        if (buttonState == ButtonState.LOADING) {
            scaleText.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500)
            )
            displayText = ""
            scaleIcon.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        } else if (buttonState == ButtonState.IDLE) {
            scaleIcon.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500)
            )
            displayText = buttonText
            scaleText.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }
    }

    return Triple(scaleText, scaleIcon, displayText)
}

@Composable
fun ButtonLoading(buttonText: String) {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    var isSelected by remember { mutableStateOf(false) }
    val (scaleText, scaleIcon, displayText) = scaleAnimation(
        buttonState = buttonState,
        isSelected = isSelected,
        buttonText = buttonText
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.primary.copy(0.1f))
            .height(40.dp)
            .clickableNoRipple {
                buttonState = ButtonState.LOADING
                isSelected = !isSelected
                coroutineScope.launch {
                    delay(2000)
                    buttonState = ButtonState.IDLE
                }

            }
    ) {
        if (buttonState == ButtonState.IDLE) {
            Text(
                text = displayText,
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .scale(scaleText.value)
            )
        } else {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .scale(scaleIcon.value)
                    .rotate(if (buttonState == ButtonState.IDLE) 0f else animateRotation()),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}