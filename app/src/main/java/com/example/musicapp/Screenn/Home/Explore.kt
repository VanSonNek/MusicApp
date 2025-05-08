package com.example.musicapp.Screenn.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.SongItem

import com.example.musicapp.R
import com.example.musicapp.Screen.Screen


import com.example.musicapp.ViewModel.DanhSachBaiHatVieModel


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
                                number = (index + 1).toString().padStart(2, '0'), // Tạo số tự động từ chỉ số vòng lặp
                                title = song["title"] as? String ?: "Không có tiêu đề",
                                artist = artistName, // Chuyển từ artistId thành artistName
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


data class Song(
    val number: String,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val luotNghe: Int// Đã thay đổi từ Int sang String
)