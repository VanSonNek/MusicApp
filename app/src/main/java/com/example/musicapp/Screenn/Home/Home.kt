package com.example.musicapp.Screen.Home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.Composable.AlbumItem
import com.example.musicapp.Composable.SongRankingItem
import com.example.musicapp.Composable.WeeklySongItem


@Composable
fun Home(modifier: Modifier = Modifier) {
    // Sử dụng LazyColumn để toàn bộ nội dung có thể cuộn
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp)) // Khoảng cách 24.dp với lớp trên
        }
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
                    contentDescription = "View All",
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
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(3) { index ->
                    AlbumItem(
                        title = "Album ${index + 1}",
                        artist = "Artist ${index + 1}"
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
                title = "Naked",
                artist = "Richie DJGY"
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

        // Music Ranking List
        val songs = listOf(
            Song("Naked", "Richie D.I.CY"),
            Song("Nâng", "Ogenus"),
            Song("Choi Như Tụi Mỹ", "Andree Right Hand"),
            Song("Faded", "Alan Walker"),
            Song("Faded Love", "Dame Dame"),
            Song("The Underground", "Dave Crusher Jleatica Chertock"),
            Song("Make You Look", "Meghan Trainor"),
            Song("Hey Mama", "Bebe Recha"),
            Song("Look What You Made...", "Taylor Swift")
        )

        itemsIndexed(songs) { index, song ->
            SongRankingItem(
                rank = index + 1,
                title = song.title,
                artist = song.artist,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Thêm khoảng trống ở dưới để đảm bảo nội dung không bị che bởi BottomNavigation
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}








data class BottomNavItem(val title: String, val icon: ImageVector)
data class Song(val title: String, val artist: String)

//    @Preview(showBackground = true)
//    @Composable
//    fun GreetingPreview() {
//        AppTheme {
//            Scaffold(
//                bottomBar = { BottomNavigation() }
//            ) { innerPadding ->
//                MusicApp(
//                    modifier = Modifier.padding(innerPadding)
//                )
//            }
//        }
//    }
