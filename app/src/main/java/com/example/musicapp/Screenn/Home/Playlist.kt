package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.MyButton
import com.example.musicapp.Composable.PlaylistHeader
import com.example.musicapp.Composable.RecentlyPlayedItem
import com.example.musicapp.Composable.SongRankingItem
import com.example.musicapp.Composable.TopBar
import com.example.musicapp.Composable.playListItem
import com.example.musicapp.R
import com.example.musicapp.Screen.Home.Song
import com.example.musicapp.Screen.Screen

@Composable
fun Playlist(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopBar(navController = navController, "Thêm bài hát vào playlist")
        },
        modifier = Modifier
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = { BottomNavigation(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlaylistHeader(
                title = "Tâm trạng",
                creator = "NGUYỄN VĂN SANH",
                imageRes = R.drawable.jack
            )
            Spacer(modifier = Modifier.height(20.dp))
            MyButton(
                onClick = {
                    navController.navigate(Screen.AddPlaylist.route)
                },
                text = "THÊM BÀI HÁT",
                backgroundColor = Color(0xFFE0E0E0),
                width = 300.dp,
                height = 55.dp,
                fontSize = 16.sp,
                textColor = Color.Black,
                cornerRadius = 5.dp,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(20.dp))

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

            LazyColumn {
                itemsIndexed(songs) { index, song ->
                    playListItem(
                        title = song.title,
                        artist = song.artist,
                        imageResId = song.imageResId, // Truyền ảnh tương ứng
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }


        }
    }
}

