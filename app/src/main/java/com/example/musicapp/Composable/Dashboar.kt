package com.example.musicapp.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.Screen.Screen

@Composable
fun Test(navController: NavController, modifier: Modifier = Modifier) {
    Text(text = "Hello Music App",
        modifier = modifier,
        fontSize = 24.sp
    )

}