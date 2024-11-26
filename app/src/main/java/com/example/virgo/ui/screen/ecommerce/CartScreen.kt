package com.example.virgo.ui.screen.ecommerce

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.VirgoTheme
import com.example.virgo.viewModel.CartViewModel
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    val viewModel : CartViewModel = viewModel()
    val productsWithQuantities = viewModel.productsWithQuantities.value
    val totalSum = viewModel.totalSum.value
    val selectAllChecked = viewModel.selectAllChecked.value

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
                    viewModel.toggleSelectAll(checked)
                }
            )
            Text(text = "Chọn tất cả (${productsWithQuantities.size})", fontSize = 16.sp)

            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                Text(text = "Đơn thuốc", color = ColorAccent)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(productsWithQuantities){product ->
                ProductItem(product) {
                    viewModel.toggleSelectOne(product)
                }
            }
        }
        fun FormattedPrice(price : Double): String {
            val formatter = NumberFormat.getNumberInstance(Locale.US) // Change Locale if needed
            return formatter.format(price)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Thành tiền: ${FormattedPrice(totalSum)}đ",
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
fun ProductItem(product: ProductWithQuantity, onCheckedChange: (Boolean) -> Unit) {
    val viewModel : CartViewModel = viewModel()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Checkbox(
            checked = product.selected?:false,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            }
        )

        AsyncImage(
            model = stringResource(R.string.github_page)+"/drawable/"+ (product.product?.images?.get(0) ?: "image_holder.jpg"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(60.dp)
        )


        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.product?.name.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = product.product?.getFormattedPrice()?:"0 đ", color = ColorAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                viewModel.decreaseQuantity(product.product?.id ?: "")
            }) {
                Text("-", fontSize = 30.sp)
            }
            Text(text = "${product.quantity}")
            IconButton(onClick = {
                viewModel.increaseQuantity(product.product?.id ?: "")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
            Text(text = product.product?.packaging?.type.toString())
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