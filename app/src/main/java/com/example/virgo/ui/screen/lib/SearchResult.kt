package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.virgo.model.Article
import com.example.virgo.model.Product
import com.example.virgo.route.ecommerce.ProductDetailRoute

@Composable
fun SearchResult (
    productResults: List<Product>,
    articleResults: List<Article>,
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
        ProductList(productResults) { product ->
            onClick(ProductDetailRoute(product.id))
        }
    } else {
//        ArticleList(articleResults) { article ->
//            onClick(ArticleRoute(article.id))
//        }
    }
}