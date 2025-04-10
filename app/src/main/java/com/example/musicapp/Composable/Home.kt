package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.Screenn.Home.BottomNavItem

@Composable
fun AlbumItem(
    title: String,
    artist: String,
    imageResId: Int, // Thêm tham số cho ảnh từ resource
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(250.dp) // Kích thước cố định phù hợp với LazyRow
            .padding(8.dp), // Khoảng cách xung quanh
        horizontalAlignment = Alignment.CenterHorizontally // Căn giữa các phần tử
    ) {
        Box(
            modifier = Modifier
                .size(250.dp) // Giảm kích thước để phù hợp hơn
                .clip(RoundedCornerShape(8.dp)) // Bo góc
                .background(Color.DarkGray) // Màu nền khi ảnh chưa tải
        ) {
            Image(
                painter = painterResource(id = imageResId), // Sử dụng ảnh từ resource
                contentDescription = "Ảnh bìa của $title",
                modifier = Modifier
                    .fillMaxSize(), // Ảnh lấp đầy Box
                contentScale = ContentScale.Crop // Cắt ảnh để vừa khung
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Khoảng cách giữa ảnh và chữ

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1, // Giới hạn 1 dòng
            overflow = TextOverflow.Ellipsis // Cắt ngắn nếu quá dài
        )

        Text(
            text = artist,
            color = Color.Gray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun WeeklySongItem(
    title: String,
    artist: String,
    imageResId: Int, // Thêm tham số cho ảnh từ resource
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
    ) {
        // Ảnh bìa album
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Ảnh bìa của $title",
            modifier = Modifier
                .fillMaxSize(), // Ảnh chiếm toàn bộ Box
            contentScale = ContentScale.Crop // Cắt ảnh để vừa khung
        )

        // Lớp phủ chứa thông tin (title và artist)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart) // Đặt ở góc dưới bên trái
                .background(Color.Black.copy(alpha = 0.5f)) // Nền mờ để chữ dễ đọc
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = artist,
                color = Color.LightGray,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SongRankingItem(
    rank: Int,
    title: String,
    artist: String,
    imageResId: Int, // Thêm tham số cho ảnh từ resource
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Số thứ hạng
        Text(
            text = String.format("%02d", rank),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(40.dp)
        )

        // Ảnh bìa bài hát
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Ảnh bìa của $title",
            modifier = Modifier
                .size(50.dp) // Kích thước ảnh nhỏ gọn
                .clip(RoundedCornerShape(4.dp)), // Bo góc nhẹ
            contentScale = ContentScale.Crop
        )

        // Khoảng cách giữa ảnh và thông tin
        Spacer(modifier = Modifier.width(12.dp))

        // Thông tin bài hát
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artist,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BottomNavigation() {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Explore", Icons.Default.Search),
        BottomNavItem("Radio", Icons.Default.Radio),
        BottomNavItem("Account", Icons.Default.AccountCircle)
    )

    // Sử dụng Navigation Bar của Material3 để tạo giao diện đẹp hơn
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.title == "Home",
                onClick = { /* Handle navigation */ },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.DarkGray
                )
            )
        }
    }
}

