package com.example.composeexplore.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PINPage() {
    val lengthPIN = 6
    var pinValue by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        BasicTextField(
            modifier = Modifier.padding(top = 16.dp),
            value = pinValue,
            onValueChange = {
                if (it.length <= lengthPIN) pinValue = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(lengthPIN) { index ->
                        val char = if (index >= pinValue.length) "" else pinValue[index].toString()
                        val currentPosition = pinValue.length == index
                        Text(
                            text = char,
                            modifier = Modifier
                                .width(40.dp)
                                .border(
                                    if (currentPosition) 2.dp else 1.dp,
                                    if (currentPosition) Color.Green else Color.LightGray,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(2.dp),
                            style = MaterialTheme.typography.h4,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        )
    }
}