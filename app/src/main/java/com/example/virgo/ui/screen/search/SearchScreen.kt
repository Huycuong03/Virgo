package com.example.virgo.ui.screen.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.model.Article
import com.example.virgo.model.Product
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.search.SearchResultRoute
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.screen.lib.SearchResult
import com.example.virgo.ui.screen.lib.SearchSuggestion
import com.example.virgo.ui.theme.ColorGradient1

@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }

    val recentSearches = listOf("sửa rửa mặt", "thuốc nhỏ mắt")
    val frequentSearches = listOf(
        "thuốc nhỏ mắt", "omega", "Men vi sinh",
        "kem chống nắng", "siro ho"
    )

    val products = listOf(
        Product(0, "Sữa rửa mặt On: The Body Rice Therapy H...", "132.000đ", "165.000đ", R.drawable.image_holder), // Replace icon_home with your placeholder
        Product(1, "Sữa rửa mặt tẩy trang Hatomugi Reihaku Ha...", "99.000đ", null, R.drawable.image_holder),
        Product(2, "Neo Cleanser", "120.000đ", null, R.drawable.image_holder)
    )

    val articles = listOf(
        Article("Sữa rửa mặt Oxy có tốt không? Một số dòng sữa rửa mặt Oxy phổ biến", R.drawable.image_holder),
        Article("Bà bầu dùng sữa rửa mặt được không? Một số loại sữa rửa mặt an toàn cho mẹ bầu", R.drawable.image_holder),
        Article("Sữa rửa mặt Cerave của nước nào? Sữa rửa mặt Cerave có tốt không?", R.drawable.image_holder),
    )

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ColorGradient1)
                .padding(vertical = 10.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }

            SearchBar(
                query = searchText,
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                },
                onChange = { searchText = it }
            ) {
                navController.navigate(SearchResultRoute(searchText))
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (searchText.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                SearchSuggestion(recentSearches = recentSearches, frequentSearch = frequentSearches) {
                    navController.navigate(SearchResultRoute(it))
                }
            } else {
                SearchResult(products, articles) { route ->
                    navController.navigate(route)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchScreen(NavController(LocalContext.current))
}

