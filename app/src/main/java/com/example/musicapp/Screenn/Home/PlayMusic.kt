package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

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
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.R

@Composable
fun PlayMusic(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color.Black
    ) { innerPadding ->
        NowPlayingScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
fun NowPlayingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Header với nút back và các action
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút quay lại
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Các nút action bên phải
            Row {
                // Nút tải xuống
//                IconButton(onClick = { /* Xử lý tải xuống */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.imgtest),
//                        contentDescription = "Download",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//
//                // Nút chia sẻ
//                IconButton(onClick = { /* Xử lý chia sẻ */ }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.jack),
//                        contentDescription = "Share",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }

                // Nút menu thêm
                IconButton(onClick = { /* Xử lý menu */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Album artwork
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF282828))
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.chidan),
                contentDescription = "Album cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Thông tin bài hát
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Naked",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Richie D.ICY",
                color = Color.Gray,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        Spacer(modifier = Modifier.height(32.dp))
        // Các nút phụ
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Thêm vào playlist */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.share2),
                    contentDescription = "Add to playlist",
                    tint = Color.LightGray
                )
            }

            IconButton(onClick = { /* Yêu thích */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.dowload),
                    contentDescription = "Favorite",
                    tint = Color.LightGray
                )
            }

            IconButton(onClick = { /* Chất lượng */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.love),
                    contentDescription = "Quality",
                    tint = Color.LightGray
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        // Thanh tiến trình
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(
                progress = 0.25f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color(0xFF1DB954),
                trackColor = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "00:00",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = "03:31",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Các nút điều khiển chính
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Shuffle */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ramdom),
                    contentDescription = "Shuffle",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(onClick = { /* Previous */ }) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Nút play lớn
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1DB954))
                    .clickable { /* Play/Pause */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            IconButton(onClick = { /* Next */ }) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { /* Repeat */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.replay),
                    contentDescription = "Repeat",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


    }
}