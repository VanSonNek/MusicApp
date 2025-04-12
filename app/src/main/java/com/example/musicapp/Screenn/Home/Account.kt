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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.ProfileHeader
import com.example.musicapp.Composable.ServicesSection
import com.example.musicapp.Composable.TopBar
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
fun AcountContent(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(1.dp)
    ) {
        // Tạo các tham chiếu
        val (topBarRef, profileHeaderRef, servicesRef) = createRefs()

        TopBar(
            modifier = Modifier
                .constrainAs(topBarRef) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ProfileHeader(
            name = "Văn Sơn",
            plan = "BASIC",
            modifier = Modifier
                .constrainAs(profileHeaderRef) {
                    top.linkTo(topBarRef.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 200.dp)
                }
        )

        ServicesSection(
            modifier = Modifier
                .constrainAs(servicesRef) {
                    top.linkTo(profileHeaderRef.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 170.dp)
                },
            serviceTitle = "Dịch vụ",
            serviceItems = listOf(
                Icons.Default.DataSaverOn to "Tiết kiệm 3G/4G truy cập",
                Icons.Default.Code to "Nhập Code"
            ),
            personalTitle = "Cá nhân",
            personalItems = listOf(
                Icons.Default.Favorite to "Danh sách yêu thích",
                Icons.Default.QueueMusic to "Danh sách Playlist",
                Icons.Default.FileDownload to "Danh sách tải xuống"
            ),
            securityTitle = "Tài khoản & Bảo mật",
            securityItems = listOf(
                Icons.Default.Lock to "Thay đổi mật khẩu",
                Icons.Default.Person to "Cập nhật thông tin cá nhân",
                Icons.Default.ExitToApp to "Đăng xuất"
            )
        )

    }
}




