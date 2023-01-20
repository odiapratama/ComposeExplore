package com.example.composeexplore.ui.component.widgets

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 72.sp,
    lineHeight: TextUnit = 80.sp,
    inputValueChanged: (String) -> Unit,
) {
    var inputValue by remember { mutableStateOf("") }
    val localContext = LocalContext.current
    val localDensity = LocalDensity.current
    var shrunkFontSize = fontSize

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {
        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = inputValue,
                style = TextStyle(
                    fontSize = shrunkFontSize,
                    fontWeight = FontWeight.Bold,
                    lineHeight = lineHeight,
                    textAlign = TextAlign.Center
                ),
                density = localDensity,
                fontFamilyResolver = createFontFamilyResolver(localContext)
            )
        }
        var intrinsics = calculateIntrinsics()

        with(localDensity) {
            val textFieldPadding = 16.dp.toPx()
            val maxInputWidth = maxWidth.toPx() - 2 * textFieldPadding

            while (intrinsics.maxIntrinsicWidth > maxInputWidth) {
                shrunkFontSize *= TEXT_SCALE_REDUCTION_INTERVAL
                intrinsics = calculateIntrinsics()
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            onValueChange = {
                inputValue = it
                inputValueChanged(it)
            },
            textStyle = TextStyle(
                fontSize = shrunkFontSize,
                fontWeight = FontWeight.SemiBold,
                lineHeight = lineHeight,
                textAlign = TextAlign.Center
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
    }
}