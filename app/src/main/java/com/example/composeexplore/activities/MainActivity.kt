package com.example.composeexplore.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexplore.R
import com.example.composeexplore.ui.theme.ComposeExploreTheme

@ExperimentalAnimationApi
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

@ExperimentalAnimationApi
@Composable
fun InitUI() {
    val counter = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hello World!")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Greeting("Android")
                NewStory("Bandung", "West Java", "Indonesia")
            }
            popular(
                listPopular = listOf(
                    "Bakso",
                    "Cuanki",
                    "Seblak",
                    "Cilok"
                )
            )
            item {
                Counter(count = counter.value) {
                    counter.value = it
                }
                PersonalCard()
                GoToList()
                GoToAnimals()
            }
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

fun LazyListScope.popular(listPopular: List<String>) {
    items(items = listPopular) { popular ->
        ItemPopular(popular = popular)
        Divider(color = Color.Black)
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

@Composable
fun PersonalCard(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable {

        }
        .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Odia Pratama", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 Minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun GoToList() {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { context.startActivity(Intent(context, ListActivity::class.java)) }) {
        Text(text = "Next")
    }
}

@ExperimentalAnimationApi
@Composable
fun GoToAnimals() {
    val context = LocalContext.current
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { context.startActivity(Intent(context, AnimalsActivity::class.java)) }) {
        Text(text = "Animals")
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeExploreTheme {
        InitUI()
    }
}