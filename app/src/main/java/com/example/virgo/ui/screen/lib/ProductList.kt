package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.virgo.model.Product

@Composable
fun ProductList(products: List<Product>, onClick: (Product) -> Unit) {
    LazyColumn (
        modifier = Modifier.fillMaxWidth()
    ) {
        items(products) { product ->
            ProductRow(product = product, onClick = onClick)
        }
    }
}

@Composable
fun ProductRow (product: Product, onClick: (Product) -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(product)
            }
    ) {
        Text(text = product.name)
    }
}