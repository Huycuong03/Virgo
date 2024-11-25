package com.example.virgo.ui.screen.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.lib.Article
import com.example.virgo.route.ArticleRoute
import com.example.virgo.route.ecommerce.ProductDetailRoute
import com.example.virgo.ui.screen.ecommerce.ProductCard
import com.example.virgo.ui.screen.lib.Gallery
import com.example.virgo.ui.screen.lib.RowItem

@Composable
fun SearchResult (
    productResultList: List<Product>,
    articleResultList: List<Article>,
    onClick: (Any) -> Unit
) {
    var selectedTab by remember {
        mutableStateOf(true)
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
        Gallery(items = productResultList) {product ->
            ProductCard(product = product as Product) {
                onClick(ProductDetailRoute(it.id.toString()))
            }
        }
    } else {
        LazyColumn (
            modifier = Modifier.padding(10.dp)
        ) {
            items(articleResultList) { article ->
                RowItem(name = article.name.toString(), description = article.description, image = article.image) {
                    article.html?.let{
                        onClick(ArticleRoute(it))
                    }
                }
            }
        }
    }
}