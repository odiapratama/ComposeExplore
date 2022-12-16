package com.example.composeexplore.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.component.ProgressButton
import com.example.composeexplore.ui.component.widgets.*
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
        val (selected, setSelected) = remember {
            mutableStateOf(0)
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SwipeButton(text = "Slide to Unlock", isComplete = isComplete) {
                coroutineScope.launch {
                    delay(2000)
                    setIsComplete(true)
                }
            }

            Divider(Modifier.padding(vertical = 16.dp))

            ProgressButton(progress = 500f) {

            }

            Divider(Modifier.padding(vertical = 16.dp))

            TypeWriterText(
                texts = listOf(
                    "Hello World!\uD83D\uDC4F",
                    "Hello Android!❤",
                    "Hello Developers!\uD83D\uDE0E"
                )
            )

            Divider(Modifier.padding(vertical = 16.dp))

            CustomTab(
                selectedItem = selected,
                items = listOf("Kotlin", "Flutter"),
                onClick = setSelected
            )

            Divider(Modifier.padding(vertical = 16.dp))

            ButtonEffects()

            Divider(Modifier.padding(vertical = 16.dp))

            ButtonLoading("Refresh")
        }
    }
}

@Composable
fun ButtonEffects() {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { },
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .bounceEffect()
        ) {
            Text(text = "Bounce")
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .width(1.dp)
        )

        Button(
            onClick = { },
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pressedEffect()
        ) {
            Text(text = "Pressed")
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight()
                .width(1.dp)
        )

        Button(
            onClick = { },
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shapedEffect()
        ) {
            Text(text = "Shaped")
        }
    }
}