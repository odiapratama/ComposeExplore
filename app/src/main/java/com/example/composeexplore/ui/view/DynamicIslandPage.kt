package com.example.composeexplore.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.composeexplore.ui.view.dynamicisland.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DynamicIslandPage() {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false
    Column {
        var islandState: IslandState by remember { mutableStateOf(IslandState.DefaultState()) }

        DynamicIsland(islandState = islandState)
        
        Spacer(modifier = Modifier.padding(vertical = 80.dp))

        RadioButtonRow(
            text = "Default",
            selected = islandState is IslandState.DefaultState
        ) {
            islandState = IslandState.DefaultState()
        }

        RadioButtonRow(
            text = "Call state",
            selected = islandState is IslandState.CallState
        ) {
            islandState = IslandState.CallState()
        }

        RadioButtonRow(
            text = "Call Timer state",
            selected = islandState is IslandState.CallTimerState
        ) {
            islandState = IslandState.CallTimerState()
        }

        RadioButtonRow(
            text = "Face unlock state",
            selected = islandState is IslandState.FaceUnlockState
        ) {
            islandState = IslandState.FaceUnlockState()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DynamicIsland(islandState: IslandState) {
    val config = LocalConfiguration.current

    val startPadding by animateDpAsState(
        targetValue = (config.screenWidthDp.dp / 2) - islandState.fullWidth / 2,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        )
    )

    val scope = rememberCoroutineScope()

    val shake = remember { Animatable(0f) }
    LaunchedEffect(islandState.hasBubbleContent) {
        scope.launch {
            shake.animateTo(15f)
            shake.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        }
    }

    MetaContainer(
        modifier = Modifier.height(200.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(start = startPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {


            MetaEntity(
                modifier = Modifier
                    .offset { IntOffset(shake.value.roundToInt(), 0) }
                    .zIndex(10f),
                metaContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(35.dp)
                            )
                    )
                }
            ) {
                IslandContent(state = islandState)
            }

            AnimatedVisibility(
                visible = islandState.hasBubbleContent,
                modifier = Modifier.padding(start = 8.dp),
                enter = bubbleEnterTransition,
                exit = bubbleExitTransition,
            ) {
                MetaEntity(
                    metaContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Color.Black,
                                    shape = RoundedCornerShape(50.dp)
                                )
                        )
                    }
                ) {
                    IslandBubbleContent(state = islandState)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private val bubbleEnterTransition = scaleIn(initialScale = .7f) + slideInHorizontally(
    animationSpec = spring(
        stiffness = Spring.StiffnessLow,
        dampingRatio = Spring.DampingRatioLowBouncy,
    )
) { -it }

@OptIn(ExperimentalAnimationApi::class)
private val bubbleExitTransition = scaleOut(targetScale = .7f) + slideOutHorizontally(
    animationSpec = spring(
        stiffness = Spring.StiffnessLow
    )
) { (-it * 1.2f).roundToInt() }

@Composable
fun RadioButtonRow(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onSelected: () -> Unit
) {

    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable {
                onSelected()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.onSurface
            )
        )

        Text(text)
    }
}