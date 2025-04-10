package com.example.musicapp.Screenn.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.MyButton
import com.example.musicapp.Composable.PlaylistHeader
import com.example.musicapp.Composable.TopBar
import com.example.musicapp.Composable.playListItem
import com.example.musicapp.R
import com.example.musicapp.Screen.Home.Song
import com.example.musicapp.Screen.Screen

@SuppressLint("ResourceAsColor")
@Composable
fun YeuThich(navController: NavController) {

    Scaffold(
        topBar = {
            TopBar(navController = navController, "Bài hát yêu thích")
        },
        modifier = Modifier
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = { BottomNavigation() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { /* Xử lý tạo playlist */ },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(color = R.color.white), // Màu nền của button
                    contentColor = Color.Black // Màu nội dung (ảnh hưởng đến màu chữ nếu không chỉ định riêng)
                )
            ){
                Text("Phát ngẫu nhiên", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))


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