package com.example.composeexplore.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.component.ProgressButton
import com.example.composeexplore.ui.component.widgets.SwipeButton
import com.example.composeexplore.ui.component.widgets.TypeWriterText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WidgetsPage() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val coroutineScope = rememberCoroutineScope()
        val (isComplete, setIsComplete) = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            SwipeButton(text = "Slide to Unlock", isComplete = isComplete) {
                coroutineScope.launch {
                    delay(2000)
                    setIsComplete(true)
                }
            }

            ProgressButton(progress = 500f) {

            }

            TypeWriterText(texts = listOf(
                "Hello World!\uD83D\uDC4F",
                "Hello Android!❤",
                "Hello Developers!\uD83D\uDE0E"
            ))
        }
    }
}