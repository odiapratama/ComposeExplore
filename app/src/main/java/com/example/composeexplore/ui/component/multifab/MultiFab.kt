package com.example.composeexplore.ui.component.multifab

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.composeexplore.R

enum class MultiFabState {
    COLLAPSED,
    EXPANDED
}

class MultiFabItem(
    val identifier: String,
    val icon: ImageBitmap,
    val label: String
)

@Composable
fun MultiFab(
    fabIcon: ImageBitmap,
    items: List<MultiFabItem>,
    showLabels: Boolean,
    toState: MultiFabState,
    stateChanged: (fabState: MultiFabState) -> Unit,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val transition = updateTransition(targetState = toState, label = "")
    val rotation: Float by transition.animateFloat(label = "rotation") { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }
    val scale: Float by transition.animateFloat(label = "scale") { state ->
        if (state == MultiFabState.EXPANDED) 56f else 0f
    }
    val alpha: Float by transition.animateFloat(
        label = "alpha",
        transitionSpec = {
            tween(50)
        }
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val shadow: Dp by transition.animateDp(
        label = "shadow",
        transitionSpec = {
            tween(50)
        }
    ) { state ->
        if (state == MultiFabState.EXPANDED) 2.dp else 0.dp
    }

    Column(horizontalAlignment = Alignment.End) {
        if (transition.currentState == MultiFabState.EXPANDED) {
            items.forEach { item ->
                MiniFab(
                    item = item,
                    alpha = alpha,
                    textShadow = shadow,
                    showLabel = showLabels,
                    buttonScale = scale,
                    onFabItemClicked = onFabItemClicked,
                    iconAlpha = alpha
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        FloatingActionButton(onClick = {
            stateChanged(
                if (transition.currentState == MultiFabState.EXPANDED) {
                    MultiFabState.COLLAPSED
                } else MultiFabState.EXPANDED
            )
        }) {
            Icon(
                bitmap = fabIcon,
                modifier = Modifier.rotate(rotation),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun MiniFab(
    item: MultiFabItem,
    alpha: Float,
    textShadow: Dp,
    showLabel: Boolean,
    buttonScale: Float,
    iconAlpha: Float,
    onFabItemClicked: (item: MultiFabItem) -> Unit
) {
    val context = LocalContext.current
    val buttonColor = MaterialTheme.colors.secondary
    val interactionSource = remember { MutableInteractionSource() }
    val shadowColor = ContextCompat.getColor(context, R.color.black)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        if (showLabel) {
            Text(
                text = item.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(animateFloatAsState(targetValue = alpha).value)
                    .shadow(textShadow)
                    .background(color = MaterialTheme.colors.surface)
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    onClick = { onFabItemClicked(item) },
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp,
                        color = MaterialTheme.colors.onSecondary
                    )
                )
        ) {
            drawCircle(
                color = Color(shadowColor),
                center = Offset(center.x + 2f, center.y + 7f),
                radius = buttonScale,
                alpha = 0.1f
            )
            drawCircle(buttonColor, buttonScale)
            drawImage(
                image = item.icon,
                topLeft = Offset(
                    (this.center.x) - (item.icon.width / 2),
                    (this.center.y) - (item.icon.width / 2)
                ),
                alpha = iconAlpha
            )
        }
    }
}