package com.example.musicapp.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PlaylistHeader(
    title: String,
    creator: String,
    imageRes: Int,  // Changed from iconRes to imageRes
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)  // Fixed height but full width
            .clickable { /* Xử lý khi nhấn */ },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image that fills the entire card
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Playlist Cover",
                contentScale = ContentScale.Crop,  // Crop to fill while maintaining aspect ratio
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Content overlay
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = creator,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun playListItem(
    title: String,
    artist: String,
    imageResId: Int, // Thêm tham số cho ảnh từ resource
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
        IconButton(
            onClick = { /* Xử lý xóa */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Xóa bài hát",
                tint = Color.Red
            )
        }
    }
}

@Composable
fun TopBar(navController: NavController, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Spacer để đẩy Text vào giữa
        Spacer(modifier = Modifier.weight(1f))

        // Text ở giữa
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.wrapContentWidth() // Đảm bảo Text không chiếm toàn bộ chiều rộng
        )

        // Spacer để đẩy Icon ra sát bên phải
        Spacer(modifier = Modifier.weight(1f))

        // Icon sát bên phải
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
            contentDescription = "Close",
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.popBackStack()}
        )
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("Tìm kiếm bài hát để thêm vào playlist") }
    var isPlaceholder by remember { mutableStateOf(true) } // Trạng thái để kiểm tra xem có đang hiển thị placeholder không

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = if (isPlaceholder) "Tìm kiếm bài hát để thêm vào playlist" else searchText,
                onValueChange = { newText ->
                    isPlaceholder = false // Khi người dùng nhập, bỏ trạng thái placeholder
                    searchText = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            // Khi nhận focus, nếu đang hiển thị placeholder thì xóa nó
                            if (isPlaceholder) {
                                searchText = ""
                                isPlaceholder = false
                            }
                        } else {
                            // Khi mất focus, nếu không có nội dung thì hiển thị lại placeholder
                            if (searchText.isEmpty()) {
                                isPlaceholder = true
                            }
                        }
                    },
                singleLine = true,
                textStyle = TextStyle(
                    color = if (isPlaceholder) Color.Gray else Color.Black,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
fun AddPlaylists(
    title: String,
    artist: String,
    imageResId: Int, // Thêm tham số cho ảnh từ resource
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
        IconButton(
            onClick = { /* Xử lý xóa */ },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Thêm bài hát",
                tint = Color.Black
            )
        }
    }
}