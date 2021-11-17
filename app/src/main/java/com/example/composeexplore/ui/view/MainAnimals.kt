package com.example.composeexplore.ui.view

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MainAnimals(navController: NavController) {
    Button(onClick = { navController.navigate("detail") }) {
        Text(text = "Next")
    }
}