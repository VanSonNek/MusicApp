package com.example.musicapp.Screenn.Home

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.R
import com.example.musicapp.ViewModel.DanhSachBaiHatVieModel
import com.example.musicapp.ViewModel.PlaylistViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.delay

@Composable
fun PlayMusic(navController: NavHostController, songId: String) {
    val viewModel: DanhSachBaiHatVieModel = viewModel()
    val context = LocalContext.current
    var showAddToPlaylistDialog by remember { mutableStateOf(false) } // Khai báo biến ở đầu hàm

    // Tìm bài hát theo ID
    val song by remember(songId) {
        derivedStateOf {
            viewModel.songsList.find { it["id"] == songId }
        }
    }

    val songUrl = (song?.get("songurl") as? String)?.trim() ?: ""
    Log.d("PlayMusic", "Song URL (cleaned): '$songUrl'")

    // Khởi tạo ExoPlayer một lần
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Mỗi khi songUrl thay đổi, set lại bài hát cho ExoPlayer
    LaunchedEffect(songUrl) {
        if (songUrl.isNotEmpty()) {
            try {
                val mediaItem = MediaItem.fromUri(Uri.parse(songUrl))
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            } catch (e: Exception) {
                Log.e("PlayMusic", "Error playing song: ${e.message}")
            }
        }
    }

    // Cleanup khi composable bị hủy
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Đang phát",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFF212121))
                ) {
                    DropdownMenuItem(
                        text = { Text("Thêm vào yêu thích", color = Color.White) },
                        onClick = {
                            // TODO: Add to favorites
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Thêm vào playlist", color = Color.White) },
                        onClick = {
                            showAddToPlaylistDialog = true // Biến đã được khai báo
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Chia sẻ", color = Color.White) },
                        onClick = {
                            // TODO: Share
                            expanded = false
                        }
                    )
                }
            }

            // Song Image
            Image(
                painter = rememberAsyncImagePainter(song?.get("imageUrl") ?: ""),
                contentDescription = "Song Cover",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Song Info
            Text(
                text = song?.get("title") as? String ?: "Unknown Title",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = song?.get("artist") as? String ?: "Unknown Artist",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progress Bar
            var progress by remember { mutableStateOf(0f) }
            var currentPosition by remember { mutableStateOf("0:00") }
            var duration by remember { mutableStateOf("0:00") }

            // Update progress periodically
            LaunchedEffect(exoPlayer) {
                while (true) {
                    if (exoPlayer.duration > 0) {
                        progress = exoPlayer.currentPosition.toFloat() / exoPlayer.duration.toFloat()
                        currentPosition = formatTime(exoPlayer.currentPosition)
                        duration = formatTime(exoPlayer.duration)
                    }
                    delay(1000) // Update every second
                }
            }

            // Time and Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = currentPosition, color = Color.Gray)
                Text(text = duration, color = Color.Gray)
            }

            Slider(
                value = progress,
                onValueChange = { newValue ->
                    progress = newValue
                    exoPlayer.seekTo((exoPlayer.duration * newValue).toLong())
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF1DB954),
                    activeTrackColor = Color(0xFF1DB954),
                    inactiveTrackColor = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Control Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* TODO: Previous */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chidan),
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                var isPlaying by remember { mutableStateOf(true) }
                IconButton(
                    onClick = {
                        isPlaying = !isPlaying
                        if (isPlaying) exoPlayer.play() else exoPlayer.pause()
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFF1DB954), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isPlaying) R.drawable.nang else R.drawable.nang
                        ),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(onClick = { /* TODO: Next */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bacphan),
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Hiển thị dialog chọn playlist khi showAddToPlaylistDialog = true
        if (showAddToPlaylistDialog) {
            AddToPlaylistDialog(
                songId = songId,
                onDismiss = { showAddToPlaylistDialog = false }
            )
        }
    }
}

@Composable
fun AddToPlaylistDialog(songId: String, onDismiss: () -> Unit) {
    val playlistViewModel: PlaylistViewModel = viewModel()
    val userPlaylists by playlistViewModel.userPlaylists
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chọn playlist",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                if (userPlaylists.isEmpty()) {
                    item {
                        Text("Bạn chưa có playlist nào")
                    }
                } else {
                    items(userPlaylists) { playlist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    playlistViewModel.addSongToPlaylist(
                                        playlistId = playlist.id,
                                        songId = songId
                                    ) { success, message ->
                                        if (success) {
                                            Toast.makeText(
                                                context,
                                                "Đã thêm vào playlist ${playlist.title}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                message ?: "Lỗi khi thêm vào playlist",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        onDismiss()
                                    }
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(playlist.coverImageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = playlist.title,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Đóng")
            }
        }
    )
}

private fun formatTime(timeMs: Long): String {
    val totalSeconds = timeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}
