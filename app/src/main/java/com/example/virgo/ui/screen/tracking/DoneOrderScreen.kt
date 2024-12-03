package com.example.virgo.ui.screen.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.virgo.model.Order
import com.example.virgo.ui.theme.VirgoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConpletedOrder(navController: NavController) {
    var selectedTabIndex = remember { mutableStateOf(0) } // Default tab: "Đã giao"
    val tabs = listOf("Đang xử lý", "Đang giao", "Đã giao", "Đã hủy", "Trả hàng")

    Column(modifier = Modifier.fillMaxSize()) {
        // TabRow for navigation
        TopAppBar(
            title = { Text(text = "Quản lý đơn hàng") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = Color.White,
            contentColor = Color(0xFF6200EE),
            modifier = Modifier.height(48.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex.value == index) Color.Black else Color.Gray,
                            fontWeight = if (selectedTabIndex.value== index) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display the order list only for the "Đã giao" tab
        if (selectedTabIndex.value == 0) {
            ToPay()
        }
        if (selectedTabIndex.value == 1) {
            ToShip()
        }
        if (selectedTabIndex.value == 2) {
            OrderListScreen()
        }
        if (selectedTabIndex.value == 3) {
            Canceled()
        }
    }
}

@Composable
fun OrderListScreen() {
    // Define a list of orders
    val orders = listOf(
        Order(
            orderDate = "13/11/2024",
            orderId = "#3149833",
            productName = "VIÊN SỦI BỔ SUNG VITAMIN TỔNG HỢP PLUSSZZ MAX MULTI VỊ CAM TÚYP 20V",
            totalPrice = "35.000đ"
        ),
        Order(
            orderDate = "06/11/2024",
            orderId = "#2763475",
            productName = "BĂNG KEO CÁ NHÂN KIDS BAND (PORORO) 4 SIZE 20 MIẾNG",
            totalPrice = "52.000đ"
        ),
        Order(
            orderDate = "31/10/2024",
            orderId = "#2624057",
            productName = "BÔNG TẨY TRANG TRÒN KAMICARE 120 MIẾNG",
            totalPrice = "52.000đ"
        )
    )

    // Display the list using LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
    ) {
        items(orders) { order ->
            OrderItem(
                orderDate = order.orderDate,
                orderId = order.orderId,
                productName = order.productName,
                totalPrice = order.totalPrice,
                onClick = { /* Handle click */ }
            )
        }
    }
}

@Composable
fun OrderItem(
    orderDate: String,
    orderId: String,
    productName: String,
    totalPrice: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
//        elevation = 4.sp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Đơn hàng $orderDate", fontWeight = FontWeight.Bold)
                Text(text = "Đã giao", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Nhận tại cửa hàng • $orderId", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = productName, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Thành tiền: $totalPrice", fontWeight = FontWeight.Bold)
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(text = "Mua lại", color = Color.White)
                }
            }
        }
    }
}

