package com.example.composeexplore.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.layout.firstBaselineToTop
import com.example.composeexplore.ui.theme.ComposeExploreTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            ComposeExploreTheme {
                Surface(color = MaterialTheme.colors.background) {
                    InitList()
                }
            }
        }
    }
}

@Composable
fun InitList() {
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
    ) {
        ScrollingList()
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberCoilPainter(
                request = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item $index",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ScrollingList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Column {
        Row {
           Button(onClick = {
               coroutineScope.launch {
                   scrollState.animateScrollToItem(0)
               }
           }) {
               Text(text = "Scroll to the top")
           }

           Button(onClick = {
               coroutineScope.launch {
                   scrollState.animateScrollToItem(listSize - 1)
               }
           }) {
               Text(text = "Scroll to the bottom")
           }
        }

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun CustomLayout(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurable, constraints ->
        layout(measurable.size, constraints.maxHeight) {
            // Add here
        }
    }
}

@Composable
fun CustomColumn(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurable, constraints ->
        layout(measurable.size, constraints.maxHeight) {
            val placeable = measurable.map { measurable ->
                measurable.measure(constraints)
            }
            var y = 0
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeable.forEach { placeable ->  
                    placeable.placeRelative(0, y)
                    y += placeable.height
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    ComposeExploreTheme {
        InitList()
    }
}

@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeExploreTheme {
        CustomColumn(modifier = Modifier.padding(4.dp)) {
            Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
            Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
            Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
        }
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    ComposeExploreTheme {
        CustomLayout(modifier = Modifier.padding(top = 32.dp)) {
            Text("Hi there!", Modifier.padding(top = 32.dp))
        }
    }
}