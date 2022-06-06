package com.example.composeexplore.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.composeexplore.data.model.Vehicle
import com.example.composeexplore.ui.component.cards.LocalVehicleUiInfo
import com.example.composeexplore.ui.component.cards.VehicleCard
import com.example.composeexplore.ui.component.cards.VehicleUiInfo
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@ExperimentalSnapperApi
@Composable
fun ParallaxPage() {
    val vehicles = mutableListOf<Vehicle>()
    for (i in 0..4) {
        vehicles.add(Vehicle("Truck", "https://i.pinimg.com/564x/9e/64/60/9e64604ea047602435ec6b77c50d0d2d.jpg", Color.Blue))
        vehicles.add(Vehicle("Car", "https://cdn.motor1.com/images/mgl/VA0z9/s3/tesla-roadster.webp", Color.Red))
        vehicles.add(Vehicle("Bus", "https://asset.kompas.com/crops/nF6ABoHwK0Dvjcb_VOl7Cd_grdM=/0x0:1000x667/750x500/data/photo/2019/02/14/3540509358.jpeg", Color.Black))
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val vehicleUiInfo = VehicleUiInfo.create(
            itemWidthDp = 400f,
            screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
            parallaxOffset = .33f,
            itemFadePx = .5f
        )
        val scrollState = rememberLazyListState()

        CompositionLocalProvider(LocalVehicleUiInfo provides vehicleUiInfo) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyRow(
                    state = scrollState,
                    flingBehavior = rememberSnapperFlingBehavior(scrollState)
                ) {
                    item {
                        VehicleScrollBuffer()
                    }
                    items(vehicles) { vehicle ->
                        VehicleCard(vehicle = vehicle)
                    }
                    item {
                        VehicleScrollBuffer()
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleScrollBuffer() {
    val vehicleUiInfo = LocalVehicleUiInfo.current
    Box(modifier = Modifier.width(vehicleUiInfo.centeredDp.dp))
}