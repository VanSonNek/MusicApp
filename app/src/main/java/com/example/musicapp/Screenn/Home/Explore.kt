package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.musicapp.Screen.Screen

@Composable
fun Explore(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color.Black
    ) { innerPadding ->
        GeezChartScreen(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
fun GeezChartScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null
) {
    val songs = listOf(
        Song("01", "Naked", "Richie D.Icy", R.drawable.chidan),
        Song("02", "Nàng", "Ogenus", R.drawable.nang),
        Song("03", "Chơi Như Tụi Mỹ", "Andree Right Hand", R.drawable.jack),
        Song("04", "Faded", "Alan Walker", R.drawable.imgtest),
        Song("05", "Faded Love", "Dame Dague", R.drawable.jackandvirut)
    )

    Column(
        modifier = modifier
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // Header với kính lúp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            IconButton(onClick = {
                navController?.navigate(Screen.Account.route) // Sử dụng cấu trúc route của bạn
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Header Geez Chart và View All
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Geez Chart",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "View All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1DB954),
                modifier = Modifier.clickable {
                    navController?.navigate(Screen.Account.route) // Sử dụng cấu trúc route của bạn
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Danh sách bài hát
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            color = Color(0xFF1E1E1E)
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                songs.forEachIndexed { index, song ->
                    SongItem(
                        song = song,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController?.navigate(Screen.PlayMusic.route) // Sử dụng cấu trúc route của bạn
                            },
                        onMenuClick = {

                        }
                    )
                    if (index < songs.size - 1) {
                        Divider(
                            color = Color(0xFF2A2A2A),
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun SongItem(
    song: Song,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {} // Thêm callback cho menu
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Số thứ tự
        Text(
            text = song.number,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.width(32.dp)
        )

        // Album artwork
        Image(
            painter = painterResource(id = song.imageRes),
            contentDescription = "Album cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Song info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = song.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = song.artist,
                fontSize = 14.sp,
                color = Color.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Menu 3 chấm
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.LightGray
            )
        }
    }
}

data class Song(
    val number: String,
    val title: String,
    val artist: String,
    val imageRes: Int
)