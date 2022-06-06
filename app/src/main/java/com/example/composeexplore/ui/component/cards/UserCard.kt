package com.example.composeexplore.ui.component.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeexplore.ui.component.widgets.DropTarget
import com.google.accompanist.coil.rememberCoilPainter
import java.util.*

data class User(
    val name: String,
    val image: String
)

@Composable
fun UserCard(user: User) {
    val foodItems = remember { mutableStateMapOf<UUID, FoodItem>() }

    DropTarget<FoodItem>(
        modifier = Modifier
            .padding(8.dp)
            .width(120.dp)
            .fillMaxHeight(0.8f)
    ) { isInBound, foodItem ->
        val bgColor = if (isInBound) Color.Gray else Color.White

        foodItem?.let {
            if (isInBound) {
                foodItems[foodItem.id] = foodItem
            }
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .width(120.dp)
                .fillMaxHeight(0.8f)
                .background(bgColor, RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberCoilPainter(request = user.image),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            if (!foodItems.isEmpty()) {
                Text(
                    text = "$${foodItems.values.sumOf { it.price }}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${foodItems.size} Items",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}