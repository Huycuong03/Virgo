package com.example.virgo.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.model.Article
import com.example.virgo.model.Product
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.route.search.SearchResultRoute
import com.example.virgo.ui.screen.lib.Gallery
import com.example.virgo.ui.screen.lib.ProductCard
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.screen.lib.TagSection
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1

@Composable
fun SearchResultScreen(query: String, navController: NavController) {
    var searchText by remember {
        mutableStateOf(query)
    }
    var selectedTab by remember { mutableStateOf(true) }


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


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = ColorBackground)
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
                TODO()
            }
        }

        TabRow(
            selectedTabIndex = if (selectedTab) 0 else 1,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab,
                onClick = { selectedTab = true },
                text = { Text("Product") }
            )
            Tab(
                selected = !selectedTab,
                onClick = { selectedTab = false },
                text = { Text("Article") }
            )
        }

        if (selectedTab) {
            ProductGallery(products, navController)
        } else {
            ArticleGallery(articles)
        }

    }
}

@Composable
fun ProductGallery(products: List<Product>, navController: NavController) {
    val orders = listOf("Top Sellers", "Lowest price", "Highest price")
    var sortOrder by remember { mutableIntStateOf(0) }

    val sortedProducts = when (sortOrder) {
        1 -> products.sortedBy { it.price.removeSuffix("đ").replace(".", "").toInt() }
        2 -> products.sortedByDescending { it.price.removeSuffix("đ").replace(".", "").toInt() }
        else -> products
    }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text("${sortedProducts.size} product${if (sortedProducts.size > 1) "s" else ""} found")
            Spacer(modifier = Modifier.weight(1f))
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TagSection(values = orders) { order ->
                sortOrder = orders.indexOf(order)
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Gallery(items = products) { product ->
            if (product is Product) {
                ProductCard(product = product) {
                    navController.navigate(ProductDetailRoute(product.id))
                }
            }
        }
    }

}



@Composable
fun ArticleGallery(articles: List<Article>) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            Text("Tìm thấy ${articles.size} bài viết", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }

        items(articles) { article ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { /* Open article */ }
            ) {
                Image(
                    painter = painterResource(id = article.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(article.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 2)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultPreview(){
    SearchResultScreen("None", NavController(LocalContext.current))
}