package com.example.messenger.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.messenger.ui.navigation.MessengerNavGraph

@Composable
fun MessengerApp(navHostController: NavHostController = rememberNavController()) {
    MessengerNavGraph(navController = navHostController)
}