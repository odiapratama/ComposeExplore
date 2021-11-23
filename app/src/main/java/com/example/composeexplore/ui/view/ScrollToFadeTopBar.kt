package com.example.composeexplore.ui.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.max

@Composable
fun ScrollToFadeTopBar(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
        ) {
            val scrollState = rememberScrollState()

            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(top = 62.dp)
                        .background(MaterialTheme.colors.surface)
                ) {
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In pharetra posuere mauris, eget maximus ligula tempor sed. Integer massa velit, facilisis mollis lacus vel, molestie commodo neque. Morbi sagittis, magna non commodo consequat, dui neque luctus eros, vitae semper lacus elit nec lectus. Nulla efficitur, est in malesuada faucibus, nibh ex bibendum ex, quis ornare felis odio eu eros. Sed eu sagittis leo, eu facilisis neque. Nulla euismod, nisi sit amet porta eleifend, eros tortor venenatis nisi, ac egestas diam lectus sit amet velit. Donec id tincidunt nulla. Integer sollicitudin lacus metus, sit amet mollis neque suscipit id.\n" +
                            "\n" +
                            "Curabitur elementum consequat elit a volutpat. Mauris in tristique erat. Quisque placerat nisi et libero eleifend, eu sodales felis luctus. Etiam pharetra nunc augue, ac accumsan metus tincidunt eget. Curabitur id malesuada erat, at ornare eros. Suspendisse at interdum dui. Pellentesque feugiat purus arcu, in mattis orci hendrerit sit amet. Proin convallis libero sed sem facilisis condimentum.\n" +
                            "\n" +
                            "Donec at enim molestie, tempus urna eget, aliquet odio. Praesent interdum, lorem id pretium tempor, tortor nisl ultrices tellus, eget commodo neque felis non dui. Morbi et interdum sem, non pellentesque orci. Aliquam vestibulum mauris dui, non vestibulum ligula mollis vel. Integer vel semper dolor. Sed ornare, velit et vestibulum fermentum, dolor urna fringilla odio, non dictum nisi odio eu arcu. Pellentesque luctus quis ipsum et finibus. Curabitur enim mauris, condimentum tincidunt pharetra ut, tempor et velit.\n" +
                            "\n" +
                            "Etiam sed nibh in lorem consequat accumsan vitae nec urna. Aliquam a dictum libero. In commodo nulla ac magna convallis, ut rutrum tortor ullamcorper. Nulla pharetra erat quis neque maximus imperdiet. Integer consequat, neque sed eleifend finibus, magna neque rhoncus neque, id blandit felis arcu sed mi. Mauris luctus, risus quis porttitor pellentesque, ipsum dui varius libero, vel luctus justo elit non lacus. Phasellus mollis risus at ipsum tincidunt pharetra at sit amet nulla. Ut ac mauris mauris.\n" +
                            "\n" +
                            "Sed non felis eget enim malesuada convallis non sed sem. Suspendisse id mi at nulla eleifend consectetur. Praesent gravida sagittis tellus, ac iaculis augue hendrerit in. Vestibulum fringilla quis ex non dignissim. Curabitur condimentum, tellus sit amet tempus venenatis, neque augue fermentum lorem, a vulputate sapien ipsum vitae orci. Pellentesque erat turpis, vulputate at tellus eget, facilisis fringilla turpis. Nullam maximus neque placerat massa scelerisque, nec consectetur velit iaculis. Sed congue purus vitae eros tristique, ut tincidunt libero ullamcorper. Suspendisse et dapibus urna. Proin finibus, mi imperdiet laoreet condimentum, elit est posuere lacus, at dapibus magna mauris id dui. Morbi sit amet accumsan leo. Phasellus posuere ex ac nibh consectetur, nec faucibus mauris fermentum. Nam vulputate aliquam odio, vel interdum orci. Morbi sit amet sapien quam. Nullam faucibus eu sem in rutrum.\n" +
                            "\n")
                }
                FadingTopBar(
                    modifier = Modifier,
                    scrollState = scrollState,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun FadingTopBar(
    modifier: Modifier,
    scrollState: ScrollState,
    navController: NavController
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(max(0.3f, scrollState.value / scrollState.maxValue.toFloat()))
                .height(56.dp)
                .background(MaterialTheme.colors.primary)
        )

        Icon(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .clickable { navController.navigateUp() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}