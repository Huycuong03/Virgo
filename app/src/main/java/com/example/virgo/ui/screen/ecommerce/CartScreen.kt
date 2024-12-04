package com.example.virgo.ui.screen.ecommerce

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.ecommerce.CheckOutRoute
import com.example.virgo.route.ecommerce.PrescriptionRoute
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.viewModel.ecommerce.CartViewModel
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val viewModel : CartViewModel = viewModel()
    val productsWithQuantities = viewModel.productsWithQuantities.value
    val totalSum = viewModel.totalSum.value
    val selectAllChecked = viewModel.selectAllChecked.value

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF0F0F0))) {
        TopAppBar(
            title = { Text(text = "Giỏ hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                TextButton(onClick = { navController.navigate(HomeRoute) }) {
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
            TextButton(onClick = { navController.navigate(PrescriptionRoute) }) {
                Text(text = "Đơn thuốc", color = ColorAccent)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(productsWithQuantities){product ->
                ProductItem(
                    product = product,
                    onCheck = {viewModel.toggleSelectOne(product)},
                    onRemove = {viewModel.removeProductFromCart(product)}
                ) {
                    viewModel.updateQuantity(product, it)
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
            onClick = {
                val selectedCartItemIds = viewModel.getSelectedCartItemIds()
                if (selectedCartItemIds.isNotEmpty()) {
                    navController.navigate(CheckOutRoute(selectedCartItemIds))
                } else {
                    Toast.makeText(navController.context.applicationContext, "Please select at least one item", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
        ) {
            Text(text = "Mua hàng", color = Color.White)
        }
    }
}

@Composable
fun ProductItem(
    product: ProductWithQuantity,
    onCheck: () -> Unit,
    onRemove: () -> Unit,
    onUpdateQuantity: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Checkbox(
            checked = product.selected?:false,
            onCheckedChange = { checked ->
                onCheck()
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
            IconButton(onClick = {
                onRemove()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
               onUpdateQuantity(false)
            }) {
                Text("-", fontSize = 30.sp)
            }
            Text(text = "${product.quantity}")
            IconButton(onClick = {
                onUpdateQuantity(true)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
            }
            Text(text = product.product?.packaging?.type.toString())
        }
    }
}
