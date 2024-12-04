package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.route.profile.EditPersonalRoute
import com.example.virgo.viewModel.profile.ProfileViewModel

@Composable
fun PersonalInforScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Background color
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3)) // Top bar color
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Thông tin cá nhân",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        ProfileContent(){
            navController.navigate(EditPersonalRoute)
        }
    }
}

@Composable
fun ProfileContent(onClick: () -> Unit) {
    val viewModel:ProfileViewModel= viewModel()
    val user = viewModel.user.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile picture placeholder
        Box(
            modifier = Modifier
                .size(100.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ProfileInfoRow(label = "Họ và tên", value = user.name?:"")
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Số điện thoại", value = user.phoneNumber?:"")
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Email", value = user.email?:"")
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        if(user.gender == true) {
            ProfileInfoRow(label = "Giới tính", value = "Nam")
        }else{
            ProfileInfoRow(label = "Giới tính", value = "Nữ")
        }
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Mã khách hàng", value = user.id?:"")
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

        Spacer(modifier = Modifier.weight(1f))

        // Edit Information button
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3F2FD)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Chỉnh sửa thông tin",
                color = Color(0xFF2196F3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String, isLink: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF757575),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = if (isLink) Color(0xFF2196F3) else Color.Black,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}
