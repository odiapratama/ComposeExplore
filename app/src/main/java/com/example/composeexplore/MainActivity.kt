package com.example.composeexplore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
    val counter = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Greeting("Android")
        NewStory("Bandung", "West Java", "Indonesia")
        Popular(
            listPopular = listOf(
                "Bakso",
                "Cuanki",
                "Seblak",
                "Cilok"
            )
        )
        Counter(count = counter.value) {
            counter.value = it
        }
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
    LazyColumn {
        items(items = listPopular) { popular ->
            ItemPopular(popular = popular)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun ItemPopular(popular: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color.Gray else Color.Transparent
    )
    Text(
        text = popular,
        modifier = Modifier
            .padding(8.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected })
    )
    Divider(color = Color.Black)
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count % 2 == 0) Color.Green else Color.Yellow
        )
    ) {
        Text(text = "Clicked $count times")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExploreTheme {
        InitUI()
    }
}