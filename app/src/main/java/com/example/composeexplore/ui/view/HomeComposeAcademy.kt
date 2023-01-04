package com.example.composeexplore.ui.view

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import com.example.composeexplore.ui.view.cardstack.CardStackLayout
import com.example.composeexplore.utils.deck
import com.example.composeexplore.utils.videoUrl
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.chrisbanes.snapper.ExperimentalSnapperApi

enum class ComposeAcademyNavigator(val navigateId: String) {
    Main("main"),
    Widget("widgets"),
    Detail("detail"),
    Backdrop("backdrop"),
    ScrollToFade("scroll-to-fade"),
    DragAndDrop("drag-and-drop"),
    Parallax("parallax"),
    Motion("motion"),
    CardStack("card-stack"),
    VideoPlayer("video-player"),
    PIN("pin"),
    LOTTIE("pin")
}

@ExperimentalSnapperApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeComposeAcademy() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = ComposeAcademyNavigator.Main.navigateId) {
        composeNavigation(route = ComposeAcademyNavigator.Main.navigateId) {
            MainPage(navController = navController)
        }

        composeNavigation(route = ComposeAcademyNavigator.Detail.navigateId) {
            DetailPage(navController = navController)
        }

        composeNavigation(route = ComposeAcademyNavigator.Backdrop.navigateId) {
            MainBackdropScaffold()
        }

        composeNavigation(route = ComposeAcademyNavigator.ScrollToFade.navigateId) {
            ScrollToFadeTopBar(navController = navController)
        }

        composeNavigation(route = ComposeAcademyNavigator.DragAndDrop.navigateId) {
            DragAndDrop()
        }

        composeNavigation(route = ComposeAcademyNavigator.Parallax.navigateId) {
            ParallaxPage()
        }

        composeNavigation(route = ComposeAcademyNavigator.Widget.navigateId) {
            WidgetsPage()
        }

        composeNavigation(route = ComposeAcademyNavigator.CardStack.navigateId) {
            CardStackLayout(deck = deck, position = 1)
        }

        composeNavigation(route = ComposeAcademyNavigator.VideoPlayer.navigateId) {
            VideoPlayerPage(videoUrl)
        }

        composeNavigation(route = ComposeAcademyNavigator.PIN.navigateId) {
            PINPage()
        }

        composeNavigation(route = ComposeAcademyNavigator.LOTTIE.navigateId) {
            LottiePage()
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.composeNavigation(
    route: String,
    body: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
    ) {
        body()
    }
}