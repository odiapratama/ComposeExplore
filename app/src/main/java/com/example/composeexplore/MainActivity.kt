package com.example.composeexplore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.theme.ComposeExploreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExploreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    InitUI()
                }
            }
        }
    }
}

@Composable
fun InitUI() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Greeting("Android")
        NewStory("Bandung", "West Java", "Indonesia")
        Popular(listPopular = listOf(
            "Bakso",
            "Cuanki",
            "Seblak",
            "Cilok"
        ))
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun NewStory(
    city: String,
    province: String,
    country: String
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column {
            Image(
                painter = painterResource(id = R.drawable.header),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = city,
                style = typography.h6
            )
            Text(
                text = province,
                style = typography.body2
            )
            Text(
                text = country,
                style = typography.body2
            )
        }
    }
}

@Composable
fun Popular(listPopular: List<String>) {
    for (popular in listPopular) {
        Text(text = popular)
        Divider(color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExploreTheme {
        InitUI()
    }
}