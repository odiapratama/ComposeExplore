package com.example.composeexplore.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.rememberMotionLayoutState
import com.example.composeexplore.R
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun MotionPage() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        DetailPage()
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun DetailPage() {
    val motionState = rememberMotionLayoutState()
    val context = LocalContext.current
    val corners = 16.dp
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionLayoutState = motionState,
        motionScene = MotionScene(content = motionScene),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Image(
            painter = rememberCoilPainter("https://asset.kompas.com/crops/Xq_d6lFUS16rLQt4hl17Cqk2UEE=/0x0:739x493/780x390/data/photo/2020/07/28/5f1fdcdacafc4.jpg"),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.layoutId("headerImage")
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = corners, topEnd = corners)
                )
                .layoutId("contentBg")
        )

        Text(
            text = "Sate Sapi Enak Joss",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold, modifier = Modifier
                .layoutId("title")
                .fillMaxWidth()
                .padding(10.dp)
        )

        Divider(
            Modifier
                .layoutId("titleDivider")
                .fillMaxWidth()
                .padding(horizontal = 34.dp)
        )

        Text(
            text = "mang Asep Sunandar",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .layoutId("subTitle")
                .fillMaxWidth()
                .padding(6.dp)
        )

        Divider(
            Modifier
                .layoutId("subTitleDivider")
                .fillMaxWidth()
                .padding(horizontal = 34.dp)
        )

        Text(
            text = "18 November 2022",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .layoutId("date")
                .fillMaxWidth()
                .padding(6.dp)
        )

        val properties = motionProperties(id = "background")

        Row(
            modifier = Modifier
                .layoutId("actions")
                .background(properties.value.color("background")),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Share, contentDescription = "", tint = Color.White)
                    Text(text = "SHARE", color = Color.White, fontSize = 12.sp)
                }
            }

            IconButton(onClick = { }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.ThumbUp, contentDescription = "", tint = Color.White)
                    Text(text = "LIKE", color = Color.White, fontSize = 12.sp)
                }
            }

            IconButton(onClick = { }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.Star, contentDescription = "", tint = Color.White)
                    Text(text = "SAVE", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Text(
            text = "Lorem ipsum dolor sit amet",
            modifier = Modifier
                .fillMaxHeight()
                .layoutId("text")
                .padding(horizontal = 16.dp),
            fontSize = 12.sp
        )

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.layoutId("menu"), contentPadding = PaddingValues(4.dp),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
    }
}