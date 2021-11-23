package com.example.composeexplore.ui.view

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

enum class ComposeAcademyNavigator(val navigateId: String) {
    Main("main"),
    Detail("detail"),
    Backdrop("backdrop"),
    ScrollToFade("scroll-to-fade")
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeComposeAcademy() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = ComposeAcademyNavigator.Main.navigateId) {
        composable(
            ComposeAcademyNavigator.Main.navigateId,
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
        ) {
            MainPage(navController = navController)
        }

        composable(
            ComposeAcademyNavigator.Detail.navigateId,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
        ) {
            DetailPage(navController = navController)
        }

        composable(
            ComposeAcademyNavigator.Backdrop.navigateId,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
        ) {
            MainBackdropScaffold()
        }

        composable(
            ComposeAcademyNavigator.ScrollToFade.navigateId,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
        ) {
            ScrollToFadeTopBar(navController = navController)
        }
    }
}