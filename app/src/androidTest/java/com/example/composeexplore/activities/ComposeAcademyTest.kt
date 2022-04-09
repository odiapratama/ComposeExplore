package com.example.composeexplore.activities

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
class ComposeAcademyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topAppBarTest() {
        composeTestRule.setContent {
            TopAppBar(
                title = {
                    Text(text = "Compose Academy")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.MailOutline, contentDescription = null)
                    }
                }
            )
        }
    }
}