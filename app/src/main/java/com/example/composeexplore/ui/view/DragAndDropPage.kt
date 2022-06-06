package com.example.composeexplore.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeexplore.ui.component.cards.FoodCard
import com.example.composeexplore.ui.component.cards.FoodItem
import com.example.composeexplore.ui.component.cards.User
import com.example.composeexplore.ui.component.cards.UserCard
import com.example.composeexplore.ui.component.widgets.LongPressDraggable

@Composable
fun DragAndDrop() {
    val foodItems = listOf(
        FoodItem(
            name = "Seblak",
            image = "https://image.shutterstock.com/image-photo/seblak-jeletot-indonesia-traditional-food-260nw-1675869652.jpg",
            price = 150000
        ),
        FoodItem(
            name = "Bakso",
            image = "https://img-global.cpcdn.com/recipes/62bc0149e02866d8/1200x630cq70/photo.jpg",
            price = 170000
        ),
        FoodItem(
            name = "Sate",
            image = "https://asset.kompas.com/crops/Xq_d6lFUS16rLQt4hl17Cqk2UEE=/0x0:739x493/780x390/data/photo/2020/07/28/5f1fdcdacafc4.jpg",
            price = 200000
        )
    )
    val users = listOf(
        User(
            name = "Jajang",
            image = "https://loveshayariimages.in/wp-content/uploads/2021/10/1080p-boy-Sad-Cartoon-Dp-Images.jpg"
        ),
        User(
            name = "Asep",
            image = "https://image.shutterstock.com/image-illustration/cartoon-character-little-boy-points-260nw-1550040197.jpg"
        ),
        User(
            name = "Rohmat",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlyRfccWFTWCQp8ZxVNCll2fH3jncEkCnJpwBv8JyxZfm6voTBhKPzT7JUwDlPwhF-XNM&usqp=CAU"
        )
    )

    Scaffold {
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(foodItems) { food ->
                    FoodCard(item = food)
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                    ) {
                        items(users) { user ->
                            UserCard(user = user)
                        }
                    }
                }
            }
        }
    }
}