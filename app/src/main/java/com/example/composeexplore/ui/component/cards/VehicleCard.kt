package com.example.composeexplore.ui.component.cards

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composeexplore.data.model.Vehicle
import com.example.composeexplore.ui.component.widgets.Mirror
import com.google.accompanist.coil.rememberCoilPainter
import kotlin.math.abs

val LocalVehicleUiInfo = staticCompositionLocalOf { VehicleUiInfo(0f, 0f, 0f, .0f, .0f) }

data class VehicleUiInfo(
    val width: Float,
    val centeredDp: Float,
    val centeredPx: Float,
    val parallaxOffset: Float,
    val parallaxFadePx: Float
) {
    companion object {
        private val screenDensity = Resources.getSystem().displayMetrics.density

        fun create(itemWidthDp: Float, screenWidthDp: Float, parallaxOffset: Float, itemFadePx: Float): VehicleUiInfo {
            val centeredItemDp = (screenWidthDp - itemWidthDp) / 2
            val centeredItemPx = centeredItemDp * screenDensity
            return VehicleUiInfo(itemWidthDp, centeredItemDp, centeredItemPx, parallaxOffset, itemWidthDp * itemFadePx * screenDensity)
        }
    }
}

@Composable
fun VehicleCard(vehicle: Vehicle) {
    val vehicleUiInfo = LocalVehicleUiInfo.current
    var itemX by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        val offsetCenterX = itemX - vehicleUiInfo.centeredPx
        val itemAlpha = ((vehicleUiInfo.parallaxFadePx - abs(offsetCenterX)) / vehicleUiInfo.parallaxFadePx).coerceAtLeast(0f)
        Text(
            text = vehicle.name,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset {
                    IntOffset(x = ((offsetCenterX * vehicleUiInfo.parallaxOffset).toInt()), y = 0)
                }
                .padding(top = 16.dp)
                .alpha(itemAlpha)
        )

        Mirror {
            Image(
                painter = rememberCoilPainter(request = vehicle.image),
                contentDescription = vehicle.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(vehicleUiInfo.width.dp)
                    .onGloballyPositioned {
                        itemX = it.positionInWindow().x
                    }
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }
}
