package com.example.musicapp.Screen.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.Composable.*
import com.example.musicapp.R

@Composable
fun Home() {
    Scaffold(
        bottomBar = { BottomNavigation() },
        containerColor = Color.Black
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .background(Color.Black)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sơn Lầy",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // New Albums Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New Albums",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* Handle view all */ }
                ) {
                    Text(
                        text = "View All",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "View All",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Album List
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Danh sách ID ảnh tương ứng với từng album
                val albumImages = listOf(
                    R.drawable.jack,
                    R.drawable.chidan,
                    R.drawable.viruss
                )
                items(3) { index ->
                    AlbumItem(
                        title = "Album ${index + 1}",
                        artist = "Artist ${index + 1}",
                        imageResId = albumImages[index] // Truyền ID ảnh
                    )
                }
            }
        }

        // Divider
        item {
            Divider(color = Color.DarkGray, thickness = 1.dp)
        }

        // Geez Weekly Section
        item {
            Text(
                text = "Geez Weekly",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Weekly Song
        item {
            WeeklySongItem(
                title = "Sự Nghiệp Chướng",
                artist = "Pháo",
                imageResId = R.drawable.sunghiepchuong
            )
        }

        // Recently Music Section
        item {
            Text(
                text = "Recently Music",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        val songs = listOf(
            Song("Sự Nghiệp Chướng", "Pháo", R.drawable.sunghiepchuong),
            Song("Nàng", "Ogenus", R.drawable.nang),
            Song("2 Phút Hơn", "Pháo ft. Masew", R.drawable.haiphuthon),
            Song("See Tình", "Hoàng Thùy Linh", R.drawable.seetinh),
            Song("Để Mị Nói Cho Mà Nghe", "Hoàng Thùy Linh", R.drawable.deminoichomanghe),
                Song("Waiting For You", "MONO", R.drawable.waitingforyou),
            Song("Hồng Nhan", "Jack", R.drawable.hongnhan),
            Song("Bạc Phận", "Jack ft. K-ICM", R.drawable.bacphan),
            Song("Là 1 Thằng Con Trai", "Jack", R.drawable.la1thangcontrai),
            Song("Một Nhà", "Da LAB", R.drawable.motnha)
        )


            itemsIndexed(songs) { index, song ->
                SongRankingItem(
                    rank = index + 1,
                    title = song.title,
                    artist = song.artist,
                    imageResId = song.imageResId, // Truyền ảnh tương ứng
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }


        // Bottom spacer
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}



data class Song(val title: String, val artist: String, val imageResId: Int)