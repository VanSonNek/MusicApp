package com.example.musicapp.Composable

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuickAccessItem(title: String, count: String, iconColor: Color, icon: ImageVector) {
    Card(
        modifier = Modifier
            .size(100.dp) // Kích thước vuông cố định (100.dp x 100.dp)
            .clickable { /* Xử lý khi nhấn */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Đảm bảo Column chiếm toàn bộ kích thước của Card
                .background(Color.White)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa theo chiều ngang
            verticalArrangement = Arrangement.Center // Căn giữa theo chiều dọc
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp) // Kích thước của icon
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center // Căn giữa icon trong Box
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(21.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp)) // Khoảng cách giữa icon và text
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.wrapContentHeight()
            )
            if (count.isNotEmpty()) {
                Text(
                    text = count,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.wrapContentHeight()
                )
            }
        }
    }
}

@Composable
fun RecentlyPlayedItem(title: String, subtitle: String, imageResId: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Xử lý khi nhấn */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thay Image bằng cách hiển thị ảnh từ resource
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray) // Placeholder cho ảnh
            ) {
                // Bạn có thể sử dụng Image với painterResource để hiển thị ảnh từ resource
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Row {
                    Text(subtitle, fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color(0xFF8A2BE2) else Color.Gray,
        modifier = Modifier
            .clickable { /* Xử lý khi nhấn */ }
            .padding(horizontal = 8.dp)
    )
}

@Composable
fun PlaylistItem(title: String, artist: String, imageResId: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Xử lý khi nhấn */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thay Image bằng cách hiển thị ảnh từ resource
        Box(
            modifier = Modifier
                .size(75.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray) // Placeholder cho ảnh
        ) {
            // Bạn có thể sử dụng Image với painterResource để hiển thị ảnh từ resource
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(artist, fontSize = 14.sp, color = Color.Gray)
        }
    }
}