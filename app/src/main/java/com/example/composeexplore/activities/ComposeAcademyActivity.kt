package com.example.composeexplore.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import com.example.composeexplore.ui.theme.ComposeExploreTheme
import com.example.composeexplore.ui.view.HomeComposeAcademy
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalSnapperApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class ComposeAcademyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExploreTheme {
                Surface(color = MaterialTheme.colors.background) {
                    InitView()
                }
            }
        }
    }
}

@ExperimentalSnapperApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun InitView() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Compose Academy")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        },
        content = {
            HomeComposeAcademy()
        }
    )
}
