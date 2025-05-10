package com.example.musicapp.Screenn.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.R
import com.example.musicapp.Screen.Screen
import com.example.musicapp.ViewModel.DanhSachBaiHatVieModel

data class Song(
    val number: String,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val luotNghe: Int
)

@Composable
fun Explore(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color.Black
    ) { innerPadding ->
        GeezChartScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
fun GeezChartScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    // Lấy danh sách bài hát từ ViewModel
    val danhsachbaihat: DanhSachBaiHatVieModel = viewModel()
    val songs = danhsachbaihat.songsList
    val artistsMap = danhsachbaihat.artistsMap // Lấy dữ liệu nghệ sĩ từ ViewModel

    Column(
        modifier = modifier
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Header với kính lúp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            IconButton(onClick = {
                navController?.navigate(Screen.Account.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Header Geez Chart và View All
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Geez Chart",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "View All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1DB954),
                modifier = Modifier.clickable {
                    navController?.navigate(Screen.Account.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Danh sách bài hát
        val sortedSongs = songs.sortedByDescending {
            val luotNghe = it["luotNghe"] as? Int ?: 0
            Log.d("GeezChartScreen", "Song: ${it["title"]}, Luot Nghe: $luotNghe")
            luotNghe
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            color = Color(0xFF1E1E1E)
        ) {
            if (sortedSongs.isEmpty()) {
                // Hiển thị khi đang tải hoặc không có dữ liệu
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Đang tải dữ liệu...", color = Color.White)
                }
            } else {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    sortedSongs.forEachIndexed { index, song ->
                        // Lấy tên nghệ sĩ từ artistId
                        val artistId = song["artistId"] as? String ?: ""
                        val artistName = artistsMap[artistId]?.get("name") as? String ?: "Không có tên nghệ sĩ"

                        SongItem(
                            song = Song(
                                number = (index + 1).toString().padStart(2, '0'),
                                title = song["title"] as? String ?: "Không có tiêu đề",
                                artist = artistName,
                                imageUrl = song["imageUrl"] as? String ?: "Không có ảnh",
                                luotNghe = song["luotNghe"] as? Int ?: 0
                            ),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController?.navigate("${Screen.PlayMusic.route}/${song["id"]}")
                                },
                            onMenuClick = {}
                        )
                        if (index < sortedSongs.size - 1) {
                            Divider(
                                color = Color(0xFF2A2A2A),
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Số thứ tự
        Text(
            text = song.number,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(30.dp)
        )

        // Ảnh bài hát
        Image(
            painter = rememberAsyncImagePainter(song.imageUrl),
            contentDescription = "Song Cover",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Thông tin bài hát
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = song.artist,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${song.luotNghe} lượt nghe",
                    color = Color(0xFF1DB954),
                    fontSize = 12.sp
                )
            }
        }

        // Nút menu
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = Color.White
            )
        }
    }
}
