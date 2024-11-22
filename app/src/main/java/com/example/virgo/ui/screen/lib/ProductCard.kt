package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.model.Product

@Composable
fun ProductCard (product: Product, onClick: (Product) -> Unit) {
    Box(
        modifier = Modifier.background(Color.White, RoundedCornerShape(4.dp)).clickable { onClick(product) }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .height(250.dp)
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