package com.example.musicapp.Screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Account : Screen("account")
}
