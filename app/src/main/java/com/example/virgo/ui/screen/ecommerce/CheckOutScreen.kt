package com.example.virgo.ui.screen.ecommerce

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.VirgoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen() {
    val products = remember {
        mutableStateListOf<Product>()
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF0F3F7))
                .padding(16.dp)
        ) {
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

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { /* TODO: Handle change delivery method */ }) {
                Text(text = "Thêm mặt hàng", color = ColorAccent)
            }
            // Product List Section
            LazyColumn {
                items(products.size) { index ->
                    ProductItem(product = products[index])
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            PaymentInfoScreen()
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        AsyncImage(
            model = (stringResource(id = R.string.github_page) + "/drawable/" + (product.images[0])),
            contentDescription = "Image Description",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.name.toString(), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = product.getFormattedPrice()+" đ", color = ColorAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
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
        // Reward Points Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Đổi 585 điểm (≈5.850đ)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5A623)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = false, onCheckedChange = { /* TODO: Handle switch */ })
        }

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
            PaymentDetailRow("Giảm giá trực tiếp", "0đ", Color(0xFFFFA500))
            PaymentDetailRow("Giảm giá voucher", "0đ", Color(0xFFFFA500))
            PaymentDetailRow("Phí vận chuyển", "Miễn phí", Color(0xFF007BFF))

            Spacer(modifier = Modifier.height(8.dp))

            // Total Amount Row
            PaymentDetailRow("Thành tiền", "570.000đ", isBold = true)

            // Reward Points Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Điểm thưởng",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "+570",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF5A623)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Terms and Conditions Text
        Text(
            text = "Bằng việc tiến hành đặt mua hàng, bạn đồng ý với Điều khoản dịch vụ và Chính sách xử lý dữ liệu cá nhân của Nhà thuốc FPT Long Châu",
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
@Preview(showBackground = false)
@Composable
fun OrderCo() {
    VirgoTheme {
        OrderConfirmationScreen()
    }
}