package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import com.example.virgo.ui.screen.ecommerce.ProductDetailScreen
import com.example.virgo.ui.theme.VirgoTheme

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        ProfileHeader()
        OrderSection()
        AccountSection()
    }
}

@Composable
fun ProfileHeader() {
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
        Text(text = "Anh Tuấn", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "0359 594 531", color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "585", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Coins",
                tint = Color.Yellow
            )
        }
    }
}

@Composable
fun OrderSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Đơn của tôi", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Image(
            painter = painterResource(id = R.drawable.order), // Placeholder image
            contentDescription = "Product Image",
            modifier = Modifier
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OrderItem(icon = Icons.Default.Star, label = "Đang xử lý")
            OrderItem(icon = Icons.Default.Place, label = "Đang giao")
            OrderItem(icon = Icons.Default.CheckCircle, label = "Đã giao")
            OrderItem(icon = Icons.Default.Refresh, label = "Đổi/Trả")
        }
    }
}

@Composable
fun OrderItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
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

@Preview(showBackground = true)
@Composable
fun sa(){
    VirgoTheme {
        ProfileScreen()
    }

}