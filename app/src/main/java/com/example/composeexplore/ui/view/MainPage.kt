package com.example.composeexplore.ui.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import com.example.composeexplore.R
import com.example.composeexplore.ui.component.widgets.MultiFab
import com.example.composeexplore.ui.component.widgets.MultiFabItem
import com.example.composeexplore.ui.component.widgets.MultiFabState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavController) {

    var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    val context = LocalContext.current
    val items = listOf(
        MultiFabItem(
            icon = ContextCompat.getDrawable(context, R.drawable.ic_add)!!
                .toBitmap()
                .asImageBitmap(),
            label = "test1"
        ),
        MultiFabItem(
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
        val scrollState = rememberScrollState()
        val alpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f
        val color = ContextCompat.getColor(context, R.color.black)
        Box(
            modifier = Modifier
                .alpha(animateFloatAsState(targetValue = alpha).value)
                .background(Color(color))
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.Widget.navigateId,
                buttonText = "Widgets"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.Detail.navigateId,
                buttonText = "Next Navigation"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.Backdrop.navigateId,
                buttonText = "Backdrop Scaffold"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.ScrollToFade.navigateId,
                buttonText = "Scroll-to-fade TopBar Scaffold"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.DragAndDrop.navigateId,
                buttonText = "Drag and Drop"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.Parallax.navigateId,
                buttonText = "Parallax"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.Motion.navigateId,
                buttonText = "Motion Layout"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.CardStack.navigateId,
                buttonText = "Card Flip"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.VideoPlayer.navigateId,
                buttonText = "Video Player"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.PIN.navigateId,
                buttonText = "PIN Layout"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.LOTTIE.navigateId,
                buttonText = "Lottie Animation"
            )

            ButtonNavigation(
                navController = navController,
                navigateId = ComposeAcademyNavigator.SWIPE_ACTION.navigateId,
                buttonText = "Swipe Action"
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ButtonNavigation(
                    navController = navController,
                    navigateId = ComposeAcademyNavigator.DYNAMIC_ISLAND.navigateId,
                    buttonText = "Dynamic Island"
                )
            }
        }
    }
}

@Composable
fun ButtonNavigation(
    navController: NavController,
    navigateId: String,
    buttonText: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { navController.navigate(navigateId) }) {
        Text(text = buttonText)
    }
}