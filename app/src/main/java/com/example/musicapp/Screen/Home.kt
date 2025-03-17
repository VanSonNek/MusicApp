package com.example.musicapp.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.NonDisposableHandle.parent
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import com.example.musicapp.Composable.Test


@Composable
fun HomeScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (testComposable) = createRefs()
        Test(
            navController = navController,
            modifier = Modifier.constrainAs(testComposable) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Account.route) { AccountScreen(navController) }
    }
}

