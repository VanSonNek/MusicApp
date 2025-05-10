package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.Model.Playlist
import com.example.musicapp.R
import com.example.musicapp.Screen.Screen
import com.example.musicapp.ViewModel.DanhSachBaiHatVieModel
import com.example.musicapp.ViewModel.PlaylistViewModel

@Composable
fun Library(navController: NavController) {
    val danhSachBaiHatVieModel: DanhSachBaiHatVieModel = viewModel()
    val playlistViewModel: PlaylistViewModel = viewModel()

    // Sá»­ dá»¥ng trá»±c tiáº¿p songsList tá»« ViewModel
    val dsBaiHat = danhSachBaiHatVieModel.songsList

    // Gá»i hĂ m Ä‘á»ƒ láº¥y dá»¯ liá»‡u khi component Ä‘Æ°á»£c táº¡o
    LaunchedEffect(Unit) {
        // Gá»i cĂ¡c phÆ°Æ¡ng thá»©c load playlist tá»« PlaylistViewModel
        playlistViewModel.loadUserPlaylists()
        playlistViewModel.loadPublicPlaylists()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181A20))
    ) {
        item {
            Text(
                text = "ThÆ° viá»‡n",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        // User Playlists Section
        item {
            UserPlaylistsSection(navController, playlistViewModel)
        }

        // Public Playlists Section
        item {
            PublicPlaylistsSection(navController, playlistViewModel)
        }

        // Recently Played Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "ÄĂ£ phĂ¡t gáº§n Ä‘Ă¢y",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Hiá»ƒn thá»‹ 5 bĂ i hĂ¡t gáº§n Ä‘Ă¢y nháº¥t
                val recentSongs = dsBaiHat.take(5)

                if (recentSongs.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ChÆ°a cĂ³ bĂ i hĂ¡t nĂ o Ä‘Æ°á»£c phĂ¡t gáº§n Ä‘Ă¢y",
                            color = Color.Gray
                        )
                    }
                } else {
                    recentSongs.forEach { song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate(Screen.PlayMusic.route + "/${song["id"]}")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(song["imageUrl"] ?: ""),
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
                                    text = song["title"] as? String ?: "Unknown",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = song["artist"] as? String ?: "Unknown",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.bacphan),
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserPlaylistsSection(navController: NavController, playlistViewModel: PlaylistViewModel = viewModel()) {
    // Láº¥y danh sĂ¡ch playlist ngÆ°á»i dĂ¹ng
    val userPlaylists = playlistViewModel.userPlaylists

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Playlist cá»§a báº¡n",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { navController.navigate(Screen.AddPlaylist.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Playlist",
                    tint = Color(0xFF1DB954)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kiá»ƒm tra xem userPlaylists cĂ³ pháº£i lĂ  null khĂ´ng
        // Hoáº·c sá»­ dá»¥ng má»™t cĂ¡ch khĂ¡c Ä‘á»ƒ kiá»ƒm tra danh sĂ¡ch rá»—ng
        val hasUserPlaylists = userPlaylists != null && userPlaylists.toString() != "[]"

        if (!hasUserPlaylists) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF212121))
                    .clickable { navController.navigate(Screen.AddPlaylist.route) },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Playlist",
                        tint = Color(0xFF1DB954),
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Táº¡o playlist Ä‘áº§u tiĂªn cá»§a báº¡n",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            // Kiá»ƒm tra vĂ  chuyá»ƒn Ä‘á»•i an toĂ n
            val safeUserPlaylists = remember(userPlaylists) {
                when (userPlaylists) {
                    is List<*> -> userPlaylists.filterIsInstance<Playlist>()
                    else -> emptyList<Playlist>()
                }
            }

            if (safeUserPlaylists.isNotEmpty()) {
                LazyRow {
                    items(safeUserPlaylists) { playlist ->
                        PlaylistItem(
                            playlist = playlist,
                            onClick = {
                                navController.navigate(Screen.Playlist.route + "/${playlist.id}")
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = "KhĂ´ng thá»ƒ hiá»ƒn thá»‹ playlist",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PublicPlaylistsSection(navController: NavController, playlistViewModel: PlaylistViewModel = viewModel()) {
    // Láº¥y danh sĂ¡ch playlist cĂ´ng khai
    val publicPlaylists = playlistViewModel.publicPlaylists

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Playlist cĂ´ng khai",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kiá»ƒm tra xem publicPlaylists cĂ³ pháº£i lĂ  null khĂ´ng
        // Hoáº·c sá»­ dá»¥ng má»™t cĂ¡ch khĂ¡c Ä‘á»ƒ kiá»ƒm tra danh sĂ¡ch rá»—ng
        val hasPublicPlaylists = publicPlaylists != null && publicPlaylists.toString() != "[]"

        if (!hasPublicPlaylists) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "KhĂ´ng cĂ³ playlist cĂ´ng khai nĂ o",
                    color = Color.Gray
                )
            }
        } else {
            // Kiá»ƒm tra vĂ  chuyá»ƒn Ä‘á»•i an toĂ n
            val safePublicPlaylists = remember(publicPlaylists) {
                when (publicPlaylists) {
                    is List<*> -> publicPlaylists.filterIsInstance<Playlist>()
                    else -> emptyList<Playlist>()
                }
            }

            if (safePublicPlaylists.isNotEmpty()) {
                LazyRow {
                    items(safePublicPlaylists) { playlist ->
                        PlaylistItem(
                            playlist = playlist,
                            onClick = {
                                navController.navigate(Screen.Playlist.route + "/${playlist.id}")
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = "KhĂ´ng thá»ƒ hiá»ƒn thá»‹ playlist cĂ´ng khai",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(playlist.coverImageUrl ?: ""),
            contentDescription = "Playlist Cover",
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = playlist.title ?: "Unknown Playlist",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${playlist.totalSongs ?: 0} bĂ i hĂ¡t",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}