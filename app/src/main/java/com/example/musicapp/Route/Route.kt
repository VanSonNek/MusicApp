package com.example.musicapp.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    // Đăng nhập/Đăng ký
    object Sign_In : Screen("sign_in", "Đăng nhập", Icons.Default.Person)
    object Sign_Up : Screen("sign_up", "Đăng ký", Icons.Default.PersonAdd)
    object Sign_Up_2 : Screen("sign_up_2", "Thông tin", Icons.Default.Info)

    // Màn hình chính
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Library : Screen("library", "Library", Icons.Default.LibraryMusic)
    object Explore : Screen("explore", "Explore", Icons.Default.Explore)
    object Search : Screen("search", "Tìm kiếm", Icons.Default.Search)

    // Playlist
    object Playlist : Screen("playlist/{playlistId}", "Playlist", Icons.Default.QueueMusic)
    object AddPlaylist : Screen("addplaylist", "Thêm", Icons.Default.Add)
    object YeuThich : Screen("yeuthich", "Yêu thích", Icons.Default.Favorite)

    // Phát nhạc
    object PlayMusic : Screen("play_music/{songId}", "Phát nhạc", Icons.Default.MusicNote)
    object MusicPlayer : Screen("music_player/{songId}", "Trình phát nhạc", Icons.Default.PlayArrow)

    // Tài khoản và cài đặt
    object Account : Screen("account", "Tài khoản", Icons.Default.Person)
    object Settings : Screen("settings", "Cài đặt", Icons.Default.Settings)

    // Bổ sung
    object ArtistDetail : Screen("artist_detail/{artistId}", "Nghệ sĩ", Icons.Default.Person)
    object History : Screen("history", "Lịch sử", Icons.Default.History)
    object Notifications : Screen("notifications", "Thông báo", Icons.Default.Notifications)
}
