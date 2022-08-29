package com.example.composeexplore.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.composeexplore.ui.component.widgets.SwipeButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WidgetsPage() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val coroutineScope = rememberCoroutineScope()
        val (isComplete, setIsComplete) = remember {
            mutableStateOf(false)
        }

        SwipeButton(text = "Slide to Unlock", isComplete = isComplete) {
            coroutineScope.launch {
                delay(2000)
                setIsComplete(true)
            }
        }
    }
}