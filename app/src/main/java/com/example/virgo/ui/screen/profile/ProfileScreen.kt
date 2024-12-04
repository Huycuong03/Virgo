package com.example.virgo.ui.screen.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.User
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.route.appointment.AppointmentHistoryRoute
import com.example.virgo.route.profile.OrderTrackingRoute
import com.example.virgo.route.profile.PersonalInformationRoute
import com.example.virgo.viewModel.profile.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val user = viewModel.user.value
    BackHandler {
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        ProfileHeader(user)
        OrderSection {
            navController.navigate(OrderTrackingRoute(status = it))
        }
        AccountSection(navController)
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
        AsyncImage(
            model = user.avatarImage,
            contentDescription = "Image Description",
            modifier = Modifier.size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
fun AccountSection(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Tài khoản", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        AccountItem(icon = Icons.Default.Person, label = "Thông tin cá nhân", onClick = {navController.navigate(PersonalInformationRoute)})
        AccountItem(icon = Icons.Default.LocationOn, label = "Quản lý sổ địa chỉ", onClick = {})
        AccountItem(icon = Icons.Default.DateRange, label = "Lịch sử đặt hẹn", onClick = {navController.navigate(AppointmentHistoryRoute)})
        AccountItem(icon = Icons.Default.Favorite, label = "Đơn thuốc của tôi", onClick = {})
    }
}

@Composable
fun AccountItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
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
