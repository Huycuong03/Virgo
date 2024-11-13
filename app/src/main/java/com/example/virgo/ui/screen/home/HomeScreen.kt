package com.example.virgo.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1
import com.example.virgo.ui.theme.ColorTextSecondary

@Composable
fun HomeScreen(navController: NavController) {
    val features = listOf(
        "Home", "Search", "Ecommerce", "Reminder", "Chat"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ColorGradient1,
                        ColorGradient1.copy(alpha = 0f),
                        ColorGradient1.copy(alpha = 0f),
                        ColorGradient1.copy(alpha = 0f),
                        ColorBackground
                    )
                )
            )
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card (
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                TextButton(onClick = { navController.navigate(SearchRoute) }) {
                    Text(
                        text = "Search for products, articles, ...",
                        color = ColorTextSecondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Card {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .background(ColorBackground)
                    .padding(10.dp)
            ) {
                items(features) { feature ->
                    Card {
                        TextButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.background(Color.White)
                        ) {
                            Text(text = feature)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavController(LocalContext.current))
}