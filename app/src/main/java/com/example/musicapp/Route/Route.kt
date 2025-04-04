package com.example.musicapp.Screen

sealed class Screen(val route: String) {
    object Account : Screen("account")
    object Sign_In : Screen("sign_in")
}
