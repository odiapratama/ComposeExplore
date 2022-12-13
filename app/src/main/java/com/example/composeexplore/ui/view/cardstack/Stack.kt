package com.example.composeexplore.ui.view.cardstack

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.example.composeexplore.data.model.Card
import com.example.composeexplore.ui.component.cards.CardFaceDisplay

@Composable
fun CardStackLayout(
    deck: List<Card>,
    position: Int,
    modifier: Modifier = Modifier
) {
    val viewModel = StackViewModel()
    val statePosition by viewModel.position.observeAsState(0)
    viewModel.setDeck(deck)
    viewModel.setPosition(position)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Stack(position = statePosition, modifier = modifier.weight(0.4f), viewModel = viewModel)
        Button(onClick = { viewModel.nextDeck() }) {
            Text(text = "Flip")
        }
        Spacer(modifier = Modifier.weight(0.6f))
    }
}

@Composable
fun Stack(
    position: Int,
    modifier: Modifier = Modifier,
    viewModel: StackViewModel
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        StackLayout(
            card = viewModel.flipCard,
            leftStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.leftStack.back, modifier)
            },
            rightStack = { modifier ->
                CardFaceDisplay(cardFace = viewModel.rightStack.front, modifier)
            },
            transitionTrigger = position,
            modifier = modifier
        )
    }
}

@Composable
fun StackLayout(
    card: Card,
    leftStack: @Composable (modifier: Modifier) -> Unit,
    rightStack: @Composable (modifier: Modifier) -> Unit,
    transitionTrigger: Int,
    modifier: Modifier = Modifier
) {
    var offset by remember(transitionTrigger) { mutableStateOf(0f) }
    var flipRotation by remember(transitionTrigger) { mutableStateOf(0f) }
    val animationSpec = tween<Float>(1000, easing = CubicBezierEasing(0.4f, 0f, 0.8f, 0.8f))

    LaunchedEffect(key1 = transitionTrigger) {
        animate(initialValue = 0f, targetValue = 1f, animationSpec = animationSpec) { value, _ ->
            offset = value
        }
        animate(initialValue = 0f, targetValue = 180f, animationSpec = animationSpec) { value, _ ->
            flipRotation = value
        }
    }

    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            leftStack(modifier = Modifier.layoutId("LeftStack"))
            rightStack(modifier = Modifier.layoutId("RightStack"))
            val cardModifier = Modifier
                .layoutId("FlipCard")
                .graphicsLayer {
                    rotationY = flipRotation
                    cameraDistance = 8 * density
                }
            if (flipRotation < 90f) CardFaceDisplay(cardFace = card.back, modifier = cardModifier)
            else CardFaceDisplay(
                cardFace = card.front,
                modifier = cardModifier.graphicsLayer {
                    rotationY = 180f
                }
            )
        }
    ) { measurables, constraints ->
        val flipCardPlaceable = measurables.firstOrNull { it.layoutId == "FlipCard" }
        val leftStackPlaceable = measurables.firstOrNull { it.layoutId == "LeftStack" }
        val rightStackPlaceable = measurables.firstOrNull { it.layoutId == "RightStack" }

        layout(constraints.maxWidth, constraints.maxHeight) {
            val cardSpacing = 16.dp
            val cardWidth = ((constraints.maxWidth - cardSpacing.toPx()) / 2).toInt()

            val cardConstrains = constraints.copy(
                minWidth = minOf(constraints.minWidth, cardWidth),
                maxWidth = cardWidth
            )

            val leftStackX = 0
            val rightStackX = leftStackX + cardSpacing.toPx() + cardWidth
            val flipCardX = rightStackX * offset

            leftStackPlaceable?.measure(cardConstrains)?.place(leftStackX, 0)
            rightStackPlaceable?.measure(cardConstrains)?.place(rightStackX.toInt(), 0)
            flipCardPlaceable?.measure(cardConstrains)?.place(flipCardX.toInt(), 0)
        }
    }
}