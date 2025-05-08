//package com.example.musicapp.Screenn.Home
//
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import coil.compose.rememberImagePainter
//import com.example.musicapp.Composable.BottomNavigation
//import com.example.musicapp.Model.BaiHat
//import com.example.musicapp.R
//import com.example.musicapp.ViewModel.DanhSachBaiHatVieModel
//import com.google.android.exoplayer2.ExoPlayer
//import com.google.android.exoplayer2.MediaItem
//import kotlinx.coroutines.delay
//
//@Composable
//fun PlayMusic(navController: NavHostController,
//              songId: String,
////              danhSachBaiHat: DanhSachBaiHatVieModel
//) {
//    val song = danhSachBaiHat.songsList.find { it.id == songId }
//    Log.d("PlayMusic", "songId: $songId")
//    // Log thông tin của danh sách bài hát
//
//    Log.d("PlayMusic", "Đang tìm bài hát với ID: $songId")
//
//// Kiểm tra kết quả tìm bài hát
//    if (song != null) {
//        Log.d("PlayMusic", "Bài hát tìm thấy: ${song.title} - ${song.artistId}")
//    } else {
//        Log.d("PlayMusic", "Không tìm thấy bài hát với ID: $songId")
//    }
//    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaItem.fromUri("https://drive.google.com/uc?export=download&id=1UGE-T_j2jou47U9OJcIK2aXRGry1WUL3")
//            setMediaItem(mediaItem)
//            prepare()
//        }
//    }
//    // Giải phóng khi composable bị huỷ
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    Scaffold(
//        bottomBar = { BottomNavigation(navController) },
//        containerColor = Color.Black
//    ) { innerPadding ->
//        NowPlayingScreen(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(),
//            navController = navController,
//            exoPlayer = exoPlayer,
//            song = song
//
//        )
//    }
//}
//
//@Composable
//fun NowPlayingScreen(
//    modifier: Modifier = Modifier,
//    navController: NavHostController? = null,
//    exoPlayer: ExoPlayer,
//    song: BaiHat?
//) {
//    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
//    var progress by remember { mutableStateOf(0f) }
//
//    // Cập nhật tiến trình bài hát
//    LaunchedEffect(isPlaying) {
//        while (isPlaying) {
//            delay(1000)
//            progress = if (exoPlayer.duration > 0) {
//                exoPlayer.currentPosition.toFloat() / exoPlayer.duration
//            } else {
//                0f
//            }
//        }
//    }
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color(0xFF121212))
//            .padding(16.dp)
//    ) {
//        // Header với nút back và các action
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Nút quay lại
//            IconButton(onClick = { navController?.popBackStack() }) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.White,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//
//            // Các nút action bên phải
//            Row {
//                // Nút menu thêm
//                IconButton(onClick = { /* Xử lý menu */ }) {
//                    Icon(
//                        imageVector = Icons.Default.MoreVert,
//                        contentDescription = "More",
//                        tint = Color.White,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//        val imageUrl = song?.imageUrl ?: ""
//        val painter = rememberImagePainter(imageUrl)
//        // Album artwork
//        Box(
//            modifier = Modifier
//                .size(300.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .background(Color(0xFF282828))
//                .align(Alignment.CenterHorizontally)
//        ) {
//            Image(
//                painter = painter,
//                contentDescription = "Album cover",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxSize()
//                    .clip(RoundedCornerShape(12.dp))
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Thông tin bài hát
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = song?.title ?: "Unknown Title",
//                color = Color.White,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = song?.artistId ?: "Unknown Title",
//                color = Color.Gray,
//                fontSize = 18.sp,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            IconButton(onClick = { /* Thêm vào playlist */ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.share2),
//                    contentDescription = "Add to playlist",
//                    tint = Color.LightGray
//                )
//            }
//
//            IconButton(onClick = { /* Yêu thích */ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.dowload),
//                    contentDescription = "Favorite",
//                    tint = Color.LightGray
//                )
//            }
//
//            IconButton(onClick = { /* Chất lượng */ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.love),
//                    contentDescription = "Quality",
//                    tint = Color.LightGray
//                )
//            }
//        }
//        // Thanh tiến trình
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            LinearProgressIndicator(
//                progress = progress,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(4.dp),
//                color = Color(0xFF1DB954),
//                trackColor = Color.DarkGray
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = formatTime(exoPlayer.currentPosition),
//                    color = Color.Gray,
//                    fontSize = 12.sp
//                )
//                Text(
//                    text = formatTime(exoPlayer.duration),
//                    color = Color.Gray,
//                    fontSize = 12.sp
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Các nút điều khiển chính
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { /* Shuffle */ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ramdom),
//                    contentDescription = "Shuffle",
//                    tint = Color.LightGray,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//
//            IconButton(onClick = { /* Previous */ }) {
//                Icon(
//                    imageVector = Icons.Default.SkipPrevious,
//                    contentDescription = "Previous",
//                    tint = Color.White,
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//
//            // Nút play lớn
//            Box(
//                modifier = Modifier
//                    .size(64.dp)
//                    .clip(CircleShape)
//                    .background(Color(0xFF1DB954))
//                    .clickable {
//                        if (isPlaying) {
//                            exoPlayer.pause()
//                        } else {
//                            exoPlayer.play()
//                        }
//                        isPlaying = !isPlaying
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
//                    contentDescription = if (isPlaying) "Pause" else "Play",
//                    tint = Color.White,
//                    modifier = Modifier.size(36.dp)
//                )
//            }
//
//            IconButton(onClick = { /* Next */ }) {
//                Icon(
//                    imageVector = Icons.Default.SkipNext,
//                    contentDescription = "Next",
//                    tint = Color.White,
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//
//            IconButton(onClick = { /* Repeat */ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.replay),
//                    contentDescription = "Repeat",
//                    tint = Color.LightGray,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//    }
//}
//
//// Hàm chuyển đổi thời gian từ milliseconds sang định dạng mm:ss
//private fun formatTime(milliseconds: Long): String {
//    val totalSeconds = milliseconds / 1000
//    val minutes = totalSeconds / 60
//    val seconds = totalSeconds % 60
//    return String.format("%02d:%02d", minutes, seconds)
//}