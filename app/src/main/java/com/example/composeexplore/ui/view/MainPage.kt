package com.example.composeexplore.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import com.example.composeexplore.R
import com.example.composeexplore.ui.component.multifab.MultiFab
import com.example.composeexplore.ui.component.multifab.MultiFabItem
import com.example.composeexplore.ui.component.multifab.MultiFabState

@Composable
fun MainPage(navController: NavController) {

    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    val context = LocalContext.current
    val items = listOf(
        MultiFabItem(
            "test1",
            icon = ContextCompat.getDrawable(context, R.drawable.ic_add)!!
                .toBitmap()
                .asImageBitmap(),
            label = "test1"
        ),
        MultiFabItem(
            "test2",
            icon = ContextCompat.getDrawable(context, R.drawable.ic_add)!!
                .toBitmap()
                .asImageBitmap(),
            label = "test2"
        )
    )

    Scaffold(
        floatingActionButton = {
            MultiFab(
                fabIcon = ContextCompat.getDrawable(context, R.drawable.ic_add)!!
                    .toBitmap()
                    .asImageBitmap(),
                toState = toState,
                items = items,
                showLabels = true,
                stateChanged = { state ->
                    toState = state
                },
                onFabItemClicked = {

                }
            )
        }
    ) {
        val alpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f
        val color = ContextCompat.getColor(context, R.color.black)
        Box(
            modifier = Modifier
                .alpha(animateFloatAsState(targetValue = alpha).value)
                .background(Color(color))
                .fillMaxSize()
        )
        Button(onClick = { navController.navigate("detail") }) {
            Text(text = "Next")
        }
    }
}