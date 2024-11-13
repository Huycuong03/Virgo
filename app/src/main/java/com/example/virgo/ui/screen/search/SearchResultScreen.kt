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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R

data class Product(val name: String, val price: String, val oldPrice: String?, val imageRes: Int)
data class Article(val title: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen() {
    val products = listOf(
        Product("Sữa rửa mặt On: The Body Rice Therapy H...", "132.000đ", "165.000đ", R.drawable.image_holder), // Replace icon_home with your placeholder
        Product("Sữa rửa mặt tẩy trang Hatomugi Reihaku Ha...", "99.000đ", null, R.drawable.image_holder),
        Product("Neo Cleanser", "120.000đ", null, R.drawable.image_holder)
    )

    val articles = listOf(
        Article("Sữa rửa mặt Oxy có tốt không? Một số dòng sữa rửa mặt Oxy phổ biến", R.drawable.image_holder),
        Article("Bà bầu dùng sữa rửa mặt được không? Một số loại sữa rửa mặt an toàn cho mẹ bầu", R.drawable.image_holder),
        Article("Sữa rửa mặt Cerave của nước nào? Sữa rửa mặt Cerave có tốt không?", R.drawable.image_holder),
    )

    var selectedTab by remember { mutableStateOf("Sản phẩm") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEFEFEF))) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { /* Handle the back action */ }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            title = { Text("Sữa rửa mặt", fontSize = 20.sp) },
            actions = {
                Icon(Icons.Filled.Delete, contentDescription = "DeleteInput", Modifier.padding(horizontal = 10.dp))
            }
        )

        TabRow(
            selectedTabIndex = if (selectedTab == "Sản phẩm") 0 else 1,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == "Sản phẩm",
                onClick = { selectedTab = "Sản phẩm" },
                text = { Text("Sản phẩm") }
            )
            Tab(
                selected = selectedTab == "Bài viết",
                onClick = { selectedTab = "Bài viết" },
                text = { Text("Bài viết") }
            )
        }

        if (selectedTab == "Sản phẩm") {
            ProductList(products)
        } else {
            ArticleList(articles)
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    var sortOrder by remember { mutableStateOf("Top Sellers") }

    // Sort products based on the selected filter
    val sortedProducts = when (sortOrder) {
        "Lowest Price" -> products.sortedBy { it.price.removeSuffix("đ").replace(".", "").toInt() }
        "Highest Price" -> products.sortedByDescending { it.price.removeSuffix("đ").replace(".", "").toInt() }
        else -> products // Top Sellers logic can be applied here if you have data to sort by it
    }

    Column(modifier = Modifier.padding(8.dp)) {

        // Display the number of products found
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text("Tìm thấy ${sortedProducts.size} sản phẩm", fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
        }

        // Divider between sections
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // Filter buttons (Sort order selection)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { sortOrder = "Top Sellers" },
                modifier = Modifier.size(80.dp, 36.dp).padding(end = 4.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("Bán chạy", style = TextStyle(fontSize = 12.sp))
            }
            Button(
                onClick = { sortOrder = "Lowest Price" },
                modifier = Modifier.size(80.dp, 36.dp).padding(end = 4.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("Giá thấp", style = TextStyle(fontSize = 12.sp))
            }
            Button(
                onClick = { sortOrder = "Highest Price" },
                modifier = Modifier.size(80.dp, 36.dp).padding(end = 4.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text("Giá cao", style = TextStyle(fontSize = 12.sp))
            }
        }

        // Display products in a 2-column grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Define 2 columns for grid layout
            modifier = Modifier.padding(8.dp)
        ) {
            items(sortedProducts) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(300.dp)
                            .background(Color.White)
                    ) {
                        // Product image
                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.CenterHorizontally) // Align image to center horizontally
                        )
                        Spacer(modifier = Modifier.height(12.dp)) // Spacer for some space between image and name

                        // Product name
                        Text(
                            product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.align(Alignment.CenterHorizontally) // Align text to center
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Spacer for some space between name and price

                        // Price and old price
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Product price
                            Text(
                                product.price,
                                fontSize = 14.sp,
                                color = Color(0xFF2979FF),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(12.dp)) // Spacer for space between price and old price
                            // Old price (if exists)
                            product.oldPrice?.let {
                                Text(
                                    it,
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Spacer for space between price and button

                        // "Mua" button
                        Button(
                            onClick = { /* Add to cart action */ },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(120.dp, 35.dp)
                        ) {
                            Text("Chọn mua", style = TextStyle(fontSize = 14.sp))
                        }
                    }
                }

            }
        }
    }
}



@Composable
fun ArticleList(articles: List<Article>) {
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
    SearchResultScreen()
}