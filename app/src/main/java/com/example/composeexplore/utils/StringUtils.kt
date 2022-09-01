package com.example.composeexplore.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.streams.toList

@RequiresApi(Build.VERSION_CODES.N)
fun String.splitToCodePoints(): List<String> {
    return codePoints()
        .toList()
        .map {
            String(Character.toChars(it))
        }
}