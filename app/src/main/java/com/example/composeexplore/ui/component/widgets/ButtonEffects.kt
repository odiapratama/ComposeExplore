package com.example.composeexplore.ui.component.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

enum class ButtonState { PRESSED, IDLE, LOADING }

fun Modifier.bounceEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(targetValue = if (buttonState == ButtonState.PRESSED) 0.70f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null,
            onClick = {

            }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.pressedEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val y by animateFloatAsState(targetValue = if (buttonState == ButtonState.PRESSED) 20f else 0f)

    this
        .graphicsLayer {
            translationY = y
        }
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null,
            onClick = {

            }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}

fun Modifier.shapedEffect() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.IDLE) }
    val cornerRadius by animateDpAsState(targetValue = if (buttonState == ButtonState.PRESSED) 0.dp else 16.dp)

    this
        .clip(RoundedCornerShape(cornerRadius))
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null,
            onClick = {

            }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown(false)
                    ButtonState.PRESSED
                }
            }
        }
}