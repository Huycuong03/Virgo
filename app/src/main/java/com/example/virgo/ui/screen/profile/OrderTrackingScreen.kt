package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.ecommerce.Order
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.viewModel.profile.OrderTrackingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(status: OrderStatus, navController: NavController) {
    val viewMode: OrderTrackingViewModel = viewModel()
    val selectedStatus = viewMode.selectedStatus.value
    val orderListByStatus = viewMode.orderListByStatus.value

    LaunchedEffect(key1 = status) {
        viewMode.onSelectStatus(status)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Quản lý đơn hàng") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
        TabRow(
            selectedTabIndex = selectedStatus.index,
            containerColor = Color.White,
            contentColor = Color(0xFF6200EE),
            modifier = Modifier.height(48.dp)
        ) {
            OrderStatus.entries.forEach { status ->
                Tab(
                    selected = selectedStatus == status,
                    onClick = { viewMode.onSelectStatus(status) },
                    text = {
                        Text(
                            text = status.text,
                            color = if (selectedStatus == status) Color.Black else Color.Gray,
                            fontWeight = if (selectedStatus== status) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OrderTracking(orderList = orderListByStatus) {
            when (selectedStatus) {
                OrderStatus.PROCESSING -> Button(onClick = { viewMode.updateStatus(it.copy(status = OrderStatus.CANCELED.text)) }) {
                    Text(text = "Hủy")
                }
                OrderStatus.ON_DELIVERY -> {}
                OrderStatus.COMPLETED -> Button(onClick = { /*TODO*/ }) {
                    Text(text = "Mua lại")
                }
                OrderStatus.CANCELED -> Button(onClick = { /*TODO*/ }) {
                    Text(text = "Mua lại")
                }
            }
        }

    }
}

@Composable
fun OrderTracking(orderList: List<Order>, action: @Composable (Order) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(8.dp)
    ) {
        items(orderList) { order ->
            OrderItem(
                order = order,
                action = action
            )
        }
    }
}

@Composable
fun OrderItem(order: Order, action: @Composable (Order) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Đơn hàng ${order.timestamp?.toDate().toString()}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "#${order.id}", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = order.products.map { it.product?.name }.joinToString(), fontWeight = FontWeight.Medium, fontSize = 14.sp, maxLines = 2)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Thành tiền: ${order.payment?.total}", fontWeight = FontWeight.Bold)
                action(order)
            }
        }
    }
}

