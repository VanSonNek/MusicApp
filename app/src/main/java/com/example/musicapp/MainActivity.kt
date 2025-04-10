package com.example.musicapp
import com.example.musicapp.Screen.Screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.musicapp.Screen.Home.Home
import com.example.musicapp.Screenn.Home.AddPlaylist
import com.example.musicapp.Screenn.Home.Library
import com.example.musicapp.Screenn.Home.Playlist
import com.example.musicapp.Screenn.Home.YeuThich
import com.example.musicapp.Screenn.SignUp_In.Sign_Up
import com.example.musicapp.Screenn.SignUp_In.Sign_Up_2
import com.example.musicapp.Screenn.SignUp_In.Sign_in


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Sign_In.route) {
                composable(Screen.Sign_In.route) { Sign_in(navController) }
                composable(Screen.Sign_Up.route) { Sign_Up(navController) }
                composable(Screen.Sign_Up_2.route) { Sign_Up_2(navController) }
                composable(Screen.Home.route) { Home() }
                composable(Screen.Library.route) { Library() }
                composable(Screen.Playlist.route) { Playlist(navController) }
                composable(Screen.AddPlaylist.route) { AddPlaylist(navController) }
                composable(Screen.YeuThich.route) { YeuThich(navController) }
            }

        }
    }
}
