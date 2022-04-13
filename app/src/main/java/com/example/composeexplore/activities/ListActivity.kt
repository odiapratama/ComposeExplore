package com.example.composeexplore.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.example.composeexplore.ui.component.custom.CustomColumn
import com.example.composeexplore.ui.component.custom.CustomLayout
import com.example.composeexplore.ui.component.custom.StaggeredGrid
import com.example.composeexplore.ui.layout.customPadding
import com.example.composeexplore.ui.layout.firstBaselineToTop
import com.example.composeexplore.ui.theme.ComposeExploreTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

class ListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    CustomColumn(modifier = Modifier.padding(4.dp)) {
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

        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .customPadding(16.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            StaggeredGrid {
                for (topic in topics) {
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }

        ConstrainLayoutContent()
        LargeConstraintLayout()
        DecoupleConstraintLayout()
        TwoTexts(text1 = "Hello", text2 = "World")

        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun ConstrainLayoutContent() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, 16.dp)
            }
        ) {
            Text(text = "Button1")
        }

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button2")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, 16.dp)
            centerAround(button1.end)
        })

    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(0.5f)
        Text(
            text = "This is very very very very very very very very very long text",
            modifier = Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
        )
    }
}

@Composable
fun DecoupleConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(16.dp)
        } else {
            decoupledConstraints(32.dp)
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(text = "Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Composable
fun TwoTexts(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            text = text1,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )

        Divider(
            color = Color.Black, modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        Text(
            text = text2,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
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

@Preview
@Composable
fun ChipPreview() {
    ComposeExploreTheme {
        Chip(text = "Hello")
    }
}