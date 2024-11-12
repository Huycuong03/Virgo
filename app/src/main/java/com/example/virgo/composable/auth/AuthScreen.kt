package com.example.virgo.composable.auth

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.virgo.route.home.HomeRoute

@Composable
fun AuthScreen(navController: NavController) {
    Button(onClick = { navController.navigate(HomeRoute) }) {
        Text(text = "Log in")
    }
}