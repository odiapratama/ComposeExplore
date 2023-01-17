package com.example.composeexplore.ui.view

import android.view.MotionEvent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.composeexplore.R
import com.example.composeexplore.data.model.Email
import com.example.composeexplore.data.model.SwipeActionConfig
import com.example.composeexplore.ui.component.cards.EmailCard
import com.example.composeexplore.utils.animatedItemsIndexed
import com.example.composeexplore.utils.emailSamples
import com.example.composeexplore.utils.updateAnimatedItemsState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt

val DefaultSwipeActionsConfig = SwipeActionConfig(
    threshold = 0.4f,
    icon = Icons.Default.Menu,
    iconTint = Color.Transparent,
    background = Color.Transparent,
    dismissed = false,
    onDismissed = {},
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeActionPage() {
    val emails = remember {
        mutableStateListOf<Email>().apply {
            emailSamples.forEach {
                add(it)
            }
        }
    }
    val animatedList by updateAnimatedItemsState(newList = emails.map { it })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp)
    ) {
        animatedItemsIndexed(
            state = animatedList,
            keyAnim = { email ->
                email.id
            }
        ) { index, email ->
            val startActionConfig = SwipeActionConfig(
                threshold = .5f,
                icon = ImageVector.vectorResource(id = R.drawable.ic_add),
                iconTint = Color.Gray,
                background = Color.White,
                dismissed = false,
                onDismissed = {

                }
            )
            val endActionConfig = SwipeActionConfig(
                threshold = .5f,
                icon = ImageVector.vectorResource(id = R.drawable.ic_remove),
                iconTint = Color.White,
                background = Color.Gray,
                dismissed = true,
                onDismissed = {
                    emails.removeIf { it.id == email.id }
                }
            )

            SwipeAction(
                startActionConfig = startActionConfig,
                endActionConfig = endActionConfig,
                showTutorial = index == 0
            ) { state ->
                val animateCorner by remember {
                    derivedStateOf {
                        state.offset.value.absoluteValue > 30
                    }
                }
                val startCorner by animateDpAsState(
                    targetValue = when {
                        state.dismissDirection == DismissDirection.StartToEnd && animateCorner -> 8.dp
                        else -> 0.dp
                    }
                )
                val endCorner by animateDpAsState(
                    targetValue = when {
                        state.dismissDirection == DismissDirection.EndToStart && animateCorner -> 8.dp
                        else -> 0.dp
                    }
                )
                val elevation by animateDpAsState(
                    targetValue = when {
                        animateCorner -> 6.dp
                        else -> 2.dp
                    }
                )

                Card(
                    shape = RoundedCornerShape(
                        topStart = startCorner,
                        bottomStart = startCorner,
                        topEnd = endCorner,
                        bottomEnd = endCorner
                    ),
                    elevation = elevation
                ) {
                    EmailCard(data = email)
                }
            }
        }
    }

}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun SwipeAction(
    modifier: Modifier = Modifier,
    startActionConfig: SwipeActionConfig,
    endActionConfig: SwipeActionConfig,
    showTutorial: Boolean = false,
    content: @Composable (DismissState) -> Unit
) = BoxWithConstraints(modifier) {
    val width = constraints.maxWidth.toFloat()

    var dismissDirection: DismissDirection? by remember { mutableStateOf(null) }
    val state = rememberDismissState(
        confirmStateChange = {
            if (dismissDirection == DismissDirection.StartToEnd && it == DismissValue.DismissedToEnd) {
                startActionConfig.onDismissed()
                startActionConfig.dismissed
            } else if (dismissDirection == DismissDirection.EndToStart && it == DismissValue.DismissedToStart) {
                endActionConfig.onDismissed()
                endActionConfig.dismissed
            } else {
                false
            }
        }
    )

    var showingTutorial by remember { mutableStateOf(showTutorial) }

    if (showTutorial) {
        val infiniteTransition = rememberInfiniteTransition()
        val x by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = width * (startActionConfig.threshold) / 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = FastOutSlowInEasing, delayMillis = 1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        LaunchedEffect(key1 = x, block = {
            state.performDrag(x - state.offset.value)
        })
    }

    LaunchedEffect(key1 = Unit, block = {
        snapshotFlow { state.offset.value }
            .collect {
                dismissDirection = when {
                    it > width * startActionConfig.threshold -> DismissDirection.StartToEnd
                    it < -width * endActionConfig.threshold -> DismissDirection.EndToStart
                    else -> null
                }
            }
    })

    val haptic = LocalHapticFeedback.current
    LaunchedEffect(key1 = dismissDirection, block = {
        if (dismissDirection != null) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    })

    val dismissDirections by remember(startActionConfig, endActionConfig) {
        derivedStateOf {
            mutableSetOf<DismissDirection>().apply {
                if (startActionConfig != DefaultSwipeActionsConfig) add(DismissDirection.StartToEnd)
                if (endActionConfig != DefaultSwipeActionsConfig) add(DismissDirection.EndToStart)
            }
        }
    }

    SwipeToDismiss(
        state = state,
        modifier = Modifier
            .pointerInteropFilter {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    showingTutorial = false
                }
                false
            },
        directions = dismissDirections,
        dismissThresholds = {
            if (it == DismissDirection.StartToEnd)
                FractionalThreshold(startActionConfig.threshold)
            else
                FractionalThreshold(endActionConfig.threshold)
        },
        background = {
            AnimatedContent(
                targetState = Pair(state.dismissDirection, dismissDirection != null),
                transitionSpec = {
                    fadeIn(
                        tween(0),
                        initialAlpha = if (targetState.second) 1f else 0f
                    ) with fadeOut(
                        tween(0),
                        targetAlpha = if (targetState.second) 0.7f else 0f
                    )
                }
            ) { (direction, dismiss) ->
                val revealSize = remember { Animatable(if (dismiss) 0f else 1f) }
                val iconSize = remember { Animatable(if (dismiss) 0.8f else 1f) }

                LaunchedEffect(key1 = Unit, block = {
                    if (dismiss) {
                        revealSize.snapTo(0f)
                        launch {
                            revealSize.animateTo(1f, animationSpec = tween(400))
                        }
                        iconSize.snapTo(0.8f)
                        iconSize.animateTo(
                            1.45f,
                            spring(dampingRatio = Spring.DampingRatioHighBouncy)
                        )
                        iconSize.animateTo(
                            1f,
                            spring(dampingRatio = Spring.DampingRatioLowBouncy)
                        )
                    }
                })

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            CirclePath(
                                progress = revealSize.value,
                                start = direction == DismissDirection.StartToEnd
                            )
                        )
                        .background(
                            color = when (direction) {
                                DismissDirection.StartToEnd -> if (dismiss) startActionConfig.background else startActionConfig.iconTint
                                DismissDirection.EndToStart -> if (dismiss) startActionConfig.background else endActionConfig.iconTint
                                else -> Color.Transparent
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .align(
                                when (direction) {
                                    DismissDirection.StartToEnd -> Alignment.CenterStart
                                    else -> Alignment.CenterEnd
                                }
                            )
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .scale(iconSize.value)
                            .offset {
                                IntOffset(
                                    x = 0,
                                    y = (10 * (1f - iconSize.value)).roundToInt()
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        when (direction) {
                            DismissDirection.StartToEnd -> {
                                Image(
                                    painter = rememberVectorPainter(image = startActionConfig.icon),
                                    colorFilter = ColorFilter.tint(if (dismiss) startActionConfig.iconTint else startActionConfig.background),
                                    contentDescription = null
                                )
                            }
                            DismissDirection.EndToStart -> {
                                Image(
                                    painter = rememberVectorPainter(image = endActionConfig.icon),
                                    colorFilter = ColorFilter.tint(if (dismiss) endActionConfig.iconTint else endActionConfig.background),
                                    contentDescription = null
                                )
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    ) {
        content(state)
    }
}

class CirclePath(
    private val progress: Float,
    private val start: Boolean
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val origin = Offset(
            x = if (start) 0f else size.width,
            y = size.center.y
        )

        val radius = sqrt(
            size.height * size.height + size.width * size.width
        ) * 1f * progress

        return Outline.Generic(
            Path().apply {
                addOval(
                    Rect(
                        center = origin,
                        radius = radius
                    )
                )
            }
        )
    }
}