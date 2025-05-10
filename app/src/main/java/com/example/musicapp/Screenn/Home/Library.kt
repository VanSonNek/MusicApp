package com.example.musicapp.Screenn.Home

import LibraryViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Model.Playlist
import com.example.musicapp.Screen.Screen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Library(navController: NavHostController) {
    val viewModel: LibraryViewModel = viewModel()

    // Sử dụng lắng nghe thời gian thực thay vì chỉ lấy dữ liệu một lần
    LaunchedEffect(key1 = true) {
        viewModel.listenToPlaylists()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thư viện", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Xử lý tìm kiếm */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { /* Xử lý mic */ }) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Mic",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        },
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        LibraryContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun LibraryContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LibraryViewModel
) {
    val playlists = viewModel.playlistState
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LazyColumn(
        modifier = modifier
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Tiêu đề "Playlist của tôi"
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Playlist của tôi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Xem tất cả",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Create Playlist Button
        item {
            OutlinedButton(
                onClick = { navController.navigate(Screen.AddPlaylist.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tạo playlist", color = Color.Black)
            }
        }

        // Hiển thị thông báo đang tải
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF1DB954))
                }
            }
        }

        // Hiển thị thông báo lỗi nếu có
        errorMessage?.let {
            item {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // Playlist Items từ Firebase
        items(playlists) { playlist ->
            FirebasePlaylistItem(
                playlist = playlist,
                onClick = { },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Bottom spacer
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FirebasePlaylistItem(
    playlist: Playlist,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hiển thị ảnh bìa
        AsyncImage(
            model = playlist.coverImageUrl,
            contentDescription = playlist.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = playlist.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = playlist.description,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                if (playlist.isPublic) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = "Public",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Công khai",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Private",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Riêng tư",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${playlist.totalSongs} bài hát",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        IconButton(onClick = { /* Xử lý menu */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Thêm tùy chọn",
                tint = Color.Gray
            )
        }
    }
}

// Hàm tiện ích để định dạng ngày tháng
fun formatDate(dateString: String): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return outputFormat.format(date)
    } catch (e: Exception) {
        return ""
    }
}
