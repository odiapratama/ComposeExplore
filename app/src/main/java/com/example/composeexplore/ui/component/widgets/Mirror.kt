package com.example.composeexplore.ui.component.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

object HalfSizeShape : Shape {
    private val cornerRadius = CornerRadius(24f, 24f)

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Rounded(
        RoundRect(
            rect = Rect(
                offset = Offset(0f, size.height / 2),
                size = Size(size.width, size.height)
            ),
            bottomLeft = cornerRadius,
            bottomRight = cornerRadius
        )
    )
}

@Composable
fun Mirror(content: @Composable () -> Unit) {
    Column {
        content()
        Box(modifier = Modifier
            .graphicsLayer {
                alpha = 0.6f
                rotationY = 180f
                rotationZ = 180f
            }
            .drawWithContent {
                val colors = listOf(Color.Transparent, Color.White)
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstIn
                )
            }
            .blur(radiusX = 1.dp, radiusY = 3.dp, BlurredEdgeTreatment.Unbounded)
            .clip(HalfSizeShape)
        ) {
            content()
        }
    }
}