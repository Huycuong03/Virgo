package com.example.virgo.ui.screen.ecommerce

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.ecommerce.Product

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
            AsyncImage(
                model = (stringResource(id = R.string.github_page) + "/drawable/" + (product.images[0])),
                contentDescription = "Image Description",
                modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp)) // Spacer for some space between image and name

            // Product name
            Text(
                product.name?:"Missing",
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
                    product.getFormattedPrice()+" đ",
                    fontSize = 14.sp,
                    color = Color(0xFF2979FF),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp)) // Spacer for space between price and old price
            }
            Spacer(modifier = Modifier.weight(1f)) // Spacer for space between price and button

            // "Mua" button
            Button(
                onClick = { /* Add to cart action */ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(120.dp, 35.dp)
            ) {
                Text("Add to cart", style = TextStyle(fontSize = 14.sp))
            }
        }
    }
}