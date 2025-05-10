package com.example.musicapp.Screenn.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.MusicNote
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.Model.Song
import com.example.musicapp.R
import com.example.musicapp.ViewModel.PlaylistViewModel

// Thêm OptIn để giải quyết lỗi API thử nghiệm
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(navController: NavController, playlistId: String) {
    val playlistViewModel: PlaylistViewModel = viewModel()
    val context = LocalContext.current

    val currentPlaylist by playlistViewModel.currentPlaylist
    val playlistSongs by playlistViewModel.playlistSongs

    // Kiểm tra xem resource có tồn tại không
    val musicNoteIconExists = remember {
        try {
            context.resources.getIdentifier("ic_music_note", "drawable", context.packageName) != 0
        } catch (e: Exception) {
            false
        }
    }

    LaunchedEffect(playlistId) {
        playlistViewModel.loadPlaylistById(playlistId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20))
    ) {
        // Header
        TopAppBar(
            title = { Text("Playlist", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF181A20)
            )
        )

        // Playlist Info
        currentPlaylist?.let { playlist ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Playlist Cover
                    Image(
                        painter = rememberAsyncImagePainter(playlist.coverImageUrl),
                        contentDescription = "Playlist Cover",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = playlist.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = playlist.description,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${playlist.totalSongs} bài hát",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Play Button
                if (playlistSongs.isNotEmpty()) {
                    Button(
                        onClick = {
                            // Sửa lỗi: Sử dụng cách điều hướng đúng
                            navController.navigate("playMusic/${playlistSongs.first().id}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1DB954)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Phát tất cả",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Divider(color = Color.DarkGray, thickness = 1.dp)

            // Songs List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(playlistSongs) { song ->
                    SongItem(
                        song = song,
                        onSongClick = {
                            // Sửa lỗi: Sử dụng cách điều hướng đúng
                            navController.navigate("playMusic/${song.id}")
                        },
                        onRemoveClick = {
                            playlistViewModel.removeSongFromPlaylist(playlistId, song.id) { success, message ->
                                if (success) {
                                    // Refresh playlist
                                    playlistViewModel.loadPlaylistById(playlistId)
                                }
                            }
                        }
                    )
                }

                // Empty state
                if (playlistSongs.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Sửa lỗi: Không sử dụng try-catch xung quanh hàm composable
                            if (musicNoteIconExists) {
                                Icon(
                                    painter = painterResource(id = R.drawable.nang),
                                    contentDescription = "No Songs",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(64.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "No Songs",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(64.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Chưa có bài hát nào trong playlist",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        } ?: run {
            // Loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF1DB954))
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onSongClick: () -> Unit, onRemoveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSongClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Song thumbnail
        Image(
            painter = rememberAsyncImagePainter(song.imageUrl),
            contentDescription = "Song Thumbnail",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Lượt nghe: ${song.luotNghe}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(onClick = onRemoveClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remove from playlist",
                tint = Color.Gray
            )
        }
    }
}
