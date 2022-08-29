package com.example.composeexplore.ui.component.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.theme.SeaBlue
import kotlin.math.roundToInt

@Composable
fun SwipeIndicator(
    modifier: Modifier = Modifier,
    bgColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxHeight()
            .padding(2.dp)
            .clip(CircleShape)
            .aspectRatio(
                ratio = 1f,
                matchHeightConstraintsFirst = true
            )
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = bgColor,
            modifier = Modifier.size(36.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    text: String,
    isComplete: Boolean,
    completeImageVector: ImageVector = Icons.Rounded.Done,
    bgColor: Color = SeaBlue,
    onSwipe: () -> Unit
) {
    val width = 200.dp
    val widthInPx = with(LocalDensity.current) {
        width.toPx()
    }
    val anchors = mapOf(
        0f to 0,
        widthInPx to 1
    )
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val (swipeComplete, setSwipeComplete) = remember {
        mutableStateOf(false)
    }
    val alpha: Float by animateFloatAsState(
        targetValue = if (swipeComplete) 0f else 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )

    LaunchedEffect(key1 = swipeAbleState.currentValue) {
        if (swipeAbleState.currentValue == 1) {
            setSwipeComplete(true)
            onSwipe()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(horizontal = 48.dp, vertical = 16.dp)
            .clip(CircleShape)
            .background(bgColor)
            .animateContentSize()
            .then(
                if (swipeComplete) Modifier.width(64.dp)
                else Modifier.fillMaxWidth()
            )
            .requiredHeight(64.dp)
    ) {
        SwipeIndicator(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(alpha)
                .offset { IntOffset(swipeAbleState.offset.value.roundToInt(), 0) }
                .swipeable(
                    state = swipeAbleState,
                    anchors = anchors,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.3f)
                    },
                    orientation = Orientation.Horizontal
                ),
            bgColor = bgColor
        )

        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .padding(horizontal = 80.dp)
                .offset { IntOffset(swipeAbleState.offset.value.roundToInt(), 0) }
        )

        AnimatedVisibility(visible = swipeComplete && !isComplete) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 1.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }

        AnimatedVisibility(
            visible = isComplete,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = completeImageVector,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(44.dp)
            )
        }
    }
}