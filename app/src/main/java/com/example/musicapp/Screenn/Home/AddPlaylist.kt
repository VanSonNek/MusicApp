package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.AddPlaylists
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.SongRankingItem
import com.example.musicapp.Composable.TopBar
import com.example.musicapp.Composable.SearchBar
import com.example.musicapp.R
import com.example.musicapp.Screen.Home.Song
import com.example.musicapp.Screen.Screen

@Composable
fun AddPlaylist(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(navController = navController, title = "Playlist")
        },
        modifier = Modifier
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = { BottomNavigation(navController) } // lỗi sẽ hết
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            SearchBar()
            Spacer(modifier = Modifier.height(8.dp))

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
                    AddPlaylists(
                        title = song.title,
                        artist = song.artist,
                        imageResId = song.imageResId,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

