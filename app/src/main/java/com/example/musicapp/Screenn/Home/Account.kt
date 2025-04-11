package com.example.musicapp.Screenn.Home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.musicapp.Composable.BottomNavigation
import com.example.musicapp.Screen.Home.HomeContent

@Composable
fun Account(navController: NavHostController){
    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        containerColor = Color.Black
    ) { innerPadding ->
        AcountContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}
@Composable
fun AcountContent(modifier: Modifier = Modifier){

}