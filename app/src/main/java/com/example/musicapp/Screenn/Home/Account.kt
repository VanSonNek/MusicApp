package com.example.musicapp.Screenn.Home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.R

@Composable
fun Account(navController: NavHostController){
    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color.Black
    ) { innerPadding ->
        AcountContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
fun AcountContent(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TopBar()

        Spacer(modifier = Modifier.height(40.dp))

        ProfileHeader(name = "Văn Sơn", plan = "BASIC")

        Spacer(modifier = Modifier.height(30.dp))



        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        ServicesSection()
    }
}



@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Cá nhân", color = Color.Black, fontSize = 35.sp, fontWeight = FontWeight.Bold)
        Row {
            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Black)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black)
        }
    }
}

@Composable
fun ProfileHeader(name: String, plan: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.jack), // dùng tên ảnh của bạn
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = name, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = plan, color = Color.Gray)
        }
    }
}




@Composable
fun ServicesSection() {
    Column {
        Text("Dịch vụ", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.DataSaverOn, contentDescription = null, tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tiết kiệm 3G/4G truy cập", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Code, contentDescription = null, tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Nhập Code", color = Color.DarkGray)
        }
    }

    Spacer(modifier = Modifier.height(30.dp))

    Column {
        Text("Cá nhân", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Danh sách yêu thích", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.QueueMusic, contentDescription = null, tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Danh sách Playlist", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.FileDownload, contentDescription = null, tint = Color.DarkGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Danh sách tải xuống", color = Color.DarkGray)
        }
    }
}


