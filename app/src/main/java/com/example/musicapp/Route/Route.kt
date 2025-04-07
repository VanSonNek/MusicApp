package com.example.musicapp.Screen

sealed class Screen(val route: String) {
    object Sign_In : Screen("sign_in")
    object Sign_Up : Screen("sign_up")
    object Sign_Up_2 : Screen("sign_up_2")
    object Home : Screen("home")
}
