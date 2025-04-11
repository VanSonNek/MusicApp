package com.example.musicapp.Screenn.Home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Composable.PlaylistItem
import com.example.musicapp.Composable.QuickAccessItem
import com.example.musicapp.Composable.RecentlyPlayedItem
import com.example.musicapp.Composable.TabItem
import com.example.musicapp.R
import com.example.musicapp.Screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Library(navController: NavHostController) {
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
                    containerColor = Color(0xFFF5F5F5) // Màu nền của TopAppBar
                )
            )
        },
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color(0xFFF5F5F5) // Màu nền của toàn bộ màn hình
    ) { innerPadding ->
        LibraryContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController
        )


    }
}

@Composable
fun LibraryContent(modifier: Modifier = Modifier, navController: NavHostController)

 {
    LazyColumn(
        modifier = modifier
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Quick Access Section ("Yêu thích", "Đã tải", "Upload")
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    QuickAccessItem("Yêu thích",
                        "6",
                        Color(0xFF00BFFF),
                        Icons.Default.Favorite,
                        onClick = { navController.navigate(Screen.YeuThich.route) }
                        )
                }
                item {
                    QuickAccessItem("Đã tải", "5", Color(0xFF8A2BE2), Icons.Default.Download, onClick = {})
                }
//                item {
//                    QuickAccessItem("Upload", "", Color(0xFFFFA500), Icons.Default.CloudUpload)
//                }
//                item {
//                    QuickAccessItem("Yêu thích", "6", Color(0xFF00BFFF), Icons.Default.Favorite)
//                }
//                item {
//                    QuickAccessItem("Yêu thích", "6", Color(0xFF00BFFF), Icons.Default.Favorite)
//                }
            }
        }

       item{Spacer(modifier = Modifier.height(16.dp))}
        // Recently Played Section ("Nghe gần đây")
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nghe gần đây",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View All",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        item{Spacer(modifier = Modifier.height(10.dp))}

        // Recently Played Item
        item {
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp) // Khoảng cách giữa các item
            ) {
                RecentlyPlayedItem(
                    title = "Bài hát nghe gần đây",
                    subtitle = "Tâm trạng",
                    imageResId = R.drawable.bacphan
                )
                RecentlyPlayedItem(
                    title = "Bài hát nghe gần đây",
                    subtitle = "Tâm trạng",
                    imageResId = R.drawable.bacphan
                )
            }
        }

        item{Spacer(modifier = Modifier.height(16.dp))}

        // Tabs ("Playlist", "Album")
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TabItem("Playlist", isSelected = true)
                TabItem("Album", isSelected = false)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Create Playlist Button
        item {
            OutlinedButton(
                onClick = { /* Xử lý tạo playlist */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tạo playlist", color = Color.Black)
            }
        }

        // Playlist Items
        val playlists = listOf(
            Playlist("Tâm trạng", "Nguyễn Văn Sanh", R.drawable.chidan),
            Playlist("Độ Tộc 2", "Nguyễn Văn Sanh", R.drawable.nang)
        )

        itemsIndexed(playlists) { _, playlist ->
            PlaylistItem(
                title = playlist.title,
                artist = playlist.artist,
                imageResId = playlist.imageResId,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Bottom spacer
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}




data class Playlist(val title: String, val artist: String, val imageResId: Int)
data class BottomNavItem(val title: String, val icon: ImageVector)