package com.example.composeexplore.ui.component.widgets

import android.os.Build
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.composeexplore.utils.splitToCodePoints
import kotlinx.coroutines.delay

@Composable
fun TypeWriterText(
    texts: List<String>
) {
    var textDisplay by remember {
        mutableStateOf("")
    }

    val textChars: List<List<String>> = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            texts.map {
                it.splitToCodePoints()
            }
        } else {
            texts.map { text ->
                text.map {
                    it.toString()
                }
            }
        }
    }

    LaunchedEffect(key1 = texts) {
        textChars.forEach {
            it.forEachIndexed { index, _ ->
                textDisplay = it.take(
                    index + 1
                ).joinToString("")
                delay(150)
            }
            delay(1000)
        }
    }

    Text(
        text = textDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}