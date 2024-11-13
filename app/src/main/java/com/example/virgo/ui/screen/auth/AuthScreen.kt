package com.example.virgo.ui.screen.auth

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.virgo.route.HomeRoute

@Composable
fun AuthScreen(navController: NavController) {
    Button(onClick = { navController.navigate(HomeRoute) }) {
        Text(text = "Log in")
    }
}