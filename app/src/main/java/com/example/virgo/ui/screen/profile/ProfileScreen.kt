package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.User
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.route.profile.OrderTrackingRoute
import com.example.virgo.viewModel.profile.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val user = viewModel.user.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        ProfileHeader(user)
        OrderSection() {
            navController.navigate(OrderTrackingRoute(status = it))
        }
        AccountSection()
    }
}

@Composable
fun ProfileHeader(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0047AB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, CircleShape)
                .padding(8.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = user.name.toString(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = user.phoneNumber.toString(), color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun OrderSection(onClick: (OrderStatus) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Đơn của tôi", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        LazyRow (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(OrderStatus.entries.toList()) {
                TextButton(onClick = { onClick(it) }) {
                    Text(text = it.text)
                }
            }
        }
    }
}

@Composable
fun AccountSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Tài khoản", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        AccountItem(icon = Icons.Default.Person, label = "Thông tin cá nhân")
        AccountItem(icon = Icons.Default.LocationOn, label = "Quản lý sổ địa chỉ")
        AccountItem(icon = Icons.Default.DateRange, label = "Lịch sử đặt hẹn")
        AccountItem(icon = Icons.Default.Favorite, label = "Đơn thuốc của tôi")
    }
}

@Composable
fun AccountItem(icon: ImageVector, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 14.sp)
    }
}
