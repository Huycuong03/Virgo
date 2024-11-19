package com.example.virgo.ui.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.virgo.route.search.SearchRoute
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1

@Composable
fun HomeScreen(navController: NavController) {
//    val features = listOf(
//        "Appointment", "Reminder"
//    )
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorBackground, shape = RoundedCornerShape(50))
                .padding(15.dp)
                .clickable { navController.navigate(SearchRoute) }
        ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Search for products, articles, ...")
        }

        Spacer(modifier = Modifier.height(40.dp))

//        Card {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(4),
//                modifier = Modifier
//                    .background(ColorBackground)
//                    .padding(10.dp)
//            ) {
//                items(features) { feature ->
//                    Card {
//                        TextButton(
//                            onClick = { /*TODO*/ },
//                            modifier = Modifier.background(Color.White)
//                        ) {
//                            Text(text = feature)
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavController(LocalContext.current))
}