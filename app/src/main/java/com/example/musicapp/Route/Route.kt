package com.example.musicapp.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Sign_In : Screen("sign_in", "Đăng nhập", Icons.Default.Person)
    object Sign_Up : Screen("sign_up", "Đăng ký", Icons.Default.PersonAdd)
    object Sign_Up_2 : Screen("sign_up_2", "Thông tin", Icons.Default.Info)
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Library : Screen("library", "Library", Icons.Default.LibraryMusic)
    object Playlist : Screen("playlist", "Playlist", Icons.Default.QueueMusic)
    object AddPlaylist : Screen("addplaylist", "Thêm", Icons.Default.Add)
    object YeuThich : Screen("yeuthich", "Yêu thích", Icons.Default.Favorite)
    object Account : Screen("account", "Account", Icons.Default.Person)
    object Explore : Screen("Explore", "Explore", Icons.Default.Person)
    object PlayMusic : Screen("PlayMusic", "PlayMusic", Icons.Default.Person)
    object MusicPlayer : Screen("music_player", "Phát nhạc", Icons.Default.PlayArrow)

}
