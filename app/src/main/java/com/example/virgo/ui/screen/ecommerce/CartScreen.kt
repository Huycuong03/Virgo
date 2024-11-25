package com.example.virgo.ui.screen.ecommerce

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import com.example.virgo.model.Product1
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.VirgoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    var sum : Double = 0.0
    var selectAllChecked by remember { mutableStateOf(false) }
    val products = remember {
        mutableStateListOf(
            Product1(1, "Sữa rửa mặt", "264.000", "330.000", 2, "Tuýp", R.drawable.image_holder),
            Product1(2, "Viên uống Omexxel", "788.000", null, 1, "Hộp", R.drawable.image_holder)
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF0F0F0))) {
        TopAppBar(
            title = { Text(text = "Giỏ hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                TextButton(onClick = { /* Handle continue shopping */ }) {
                    Text(text = "Tiếp tục mua sắm", color = ColorAccent, fontSize = 14.sp)
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Checkbox(
                checked = selectAllChecked,
                onCheckedChange = { checked ->
                    selectAllChecked = checked
                    products.forEach { it.isChecked = checked }
                }
            )
            Text(text = "Chọn tất cả (${products.size})", fontSize = 16.sp)

            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                Text(text = "Đơn thuốc", color = ColorAccent)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(products.size) { index ->
                ProductItem(
                    product = products[index],
                    onCheckedChange = { checked ->
                        products[index].isChecked = checked
                        selectAllChecked = products.all { it.isChecked }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Thành tiền: ${sum}đ",
            modifier = Modifier
                .padding(horizontal = 16.dp),)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
        ) {
            Text(text = "Mua hàng", color = Color.White)
        }
    }
}

@Composable
fun ProductItem(product: Product1, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Checkbox(
            checked = product.isChecked,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            }
        )

        Image(
            painter = painterResource(id = product.imageUrl),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = product.price, color = ColorAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (product.originalPrice != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = product.originalPrice,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Decrease quantity */ }) {
                Text("-", fontSize = 30.sp)
            }
            Text(text = "${product.quantity}")
            IconButton(onClick = { /* Increase quantity */ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
            Text(text = product.unit)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CartScreenPreview() {
    VirgoTheme {
        CartScreen()
    }
}
