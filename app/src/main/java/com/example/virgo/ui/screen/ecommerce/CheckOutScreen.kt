package com.example.virgo.ui.screen.ecommerce

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.VirgoTheme
import com.example.virgo.viewModel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen() {
    val products = remember {
        mutableStateListOf<Product>()
    }
    val viewModel : CartViewModel = viewModel()
    val productsWithQuantities = viewModel.productsWithQuantities.value
    val totalSum = viewModel.totalSum.value
    val selectAllChecked = viewModel.selectAllChecked.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Xác nhận đơn hàng", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* Handle checkout logic */ },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
                    ) {
                        Text("Thanh toán", color = colorResource(id = R.color.white))
                    }
                }
            }
        }
    ) { innerPadding ->
        // Using LazyColumn for scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0F3F7))
                .padding(16.dp)
        ) {
            item {
                // Delivery Method Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hình thức nhận hàng",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                        Text(text = "Giao hàng tận nơi", color = ColorAccent)
                    }
                }
            }

            item {
                // Delivery Address Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Giao hàng tới",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { /* TODO: Handle change address */ }) {
                        Text(text = "Thay đổi", color = ColorAccent)
                    }
                }
            }

            item {
                // Add Address Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Bạn chưa có địa chỉ nhận hàng", color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* TODO: Handle add address */ },
                            colors = ButtonDefaults.buttonColors(containerColor = ColorAccent)
                        ) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Thêm địa chỉ")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                    Text(text = "Thêm mặt hàng", color = ColorAccent)
                }
            }

            // Product List Section
            val selectedProducts = productsWithQuantities.filter { it.selected == true }

            items(selectedProducts) { product ->
                ProductItem1(product)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                PaymentInfoScreen()
            }
        }
    }
}

@Composable
fun ProductItem1(product: ProductWithQuantity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

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
    }
}

@Composable
fun PaymentInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F3F7))
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // Payment Information Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Thông tin thanh toán",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Payment Details Rows
            PaymentDetailRow("Tổng tiền", "570.000đ", isBold = true)
            PaymentDetailRow("Phí vận chuyển", "Miễn phí", Color(0xFF007BFF))

            Spacer(modifier = Modifier.height(8.dp))

            // Total Amount Row
            PaymentDetailRow("Thành tiền", "570.000đ", isBold = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and Conditions Text
        Text(
            text = "Bằng việc tiến hành đặt mua hàng, bạn đồng ý với Điều khoản dịch vụ và Chính sách xử lý dữ liệu cá nhân của Nhà thuốc Virgo",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun PaymentDetailRow(label: String, amount: String, color: Color = Color.Black, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = amount,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}