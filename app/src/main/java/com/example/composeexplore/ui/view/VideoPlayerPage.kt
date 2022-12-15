package com.example.composeexplore.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayerPage(url: String) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val exoPlayer = ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(url)
                    .build()
                setMediaItem(mediaItem)
                playWhenReady = true
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                prepare()
            }

        val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

        DisposableEffect(
            AndroidView(factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            })
        ) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                    Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                    else -> Unit
                }
            }

            val lifecycle = lifecycleOwner.value.lifecycle
            lifecycle.addObserver(observer)

            onDispose {
                exoPlayer.release()
                lifecycle.removeObserver(observer)
            }
        }
    }
}
