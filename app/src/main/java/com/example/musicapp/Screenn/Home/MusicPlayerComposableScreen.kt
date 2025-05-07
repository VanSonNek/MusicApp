package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun MusicPlayerComposableScreen(navController: NavController) {
    val context = LocalContext.current

    // Khởi tạo ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    // Giải phóng khi composable bị huỷ
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Đang phát nhạc...", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))
        MusicPlayerControls(exoPlayer)
    }
}

@Composable
fun MusicPlayerControls(exoPlayer: ExoPlayer) {
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
            isPlaying = !isPlaying
        }) {
            Text(if (isPlaying) "Pause" else "Play")
        }
    }
}
