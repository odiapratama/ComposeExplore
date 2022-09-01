package com.example.composeexplore.ui.component.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TabIndicator(
    width: Dp,
    offset: Dp,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
            .offset(offset)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun TabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    width: Dp,
    text: String
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
        animationSpec = tween(easing = LinearEasing)
    )

    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(width)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        text = text,
        color = textColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CustomTab(
    selectedItem: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    width: Dp = 100.dp,
    onClick: (index: Int) -> Unit
) {
    val offset by animateDpAsState(
        targetValue = width * selectedItem,
        animationSpec = tween(easing = LinearEasing)
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .height(IntrinsicSize.Min)
    ) {
        TabIndicator(width = width, offset = offset, color = MaterialTheme.colors.primary)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape)
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItem
                TabItem(
                    isSelected = isSelected,
                    onClick = { onClick(index) },
                    width = width,
                    text = text
                )
            }
        }
    }
}