package com.example.virgo.ui.screen.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.route.profile.EditPersonalRoute
import com.example.virgo.route.profile.ProfileRoute
import com.example.virgo.viewModel.profile.ProfileViewModel

@Composable
fun PersonalInforScreen(navController: NavController) {
    val viewModel : ProfileViewModel = viewModel()
    val user = viewModel.user.value

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
                        .clickable {
                            navController.popBackStack()
                            navController.navigate(ProfileRoute)
                        }
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
        user.name?.let { user.phoneNumber?.let { it1 ->
            user.email?.let { it2 ->
                user.gender?.let { it3 ->
                    user.avatarImage?.let { it4 ->
                        ProfileContent(it,
                            it1, it2, it3, it4,
                            onClick ={
                                navController.navigate(EditPersonalRoute)
                            }
                        )
                    }
                }
            }
        }}
    }
}

@Composable
fun ProfileContent(name: String, phoneNumber: String, email: String, gender: Boolean,image: String, onClick: () -> Unit) {
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
            AsyncImage(
                model = image,
                contentDescription = "Image Description",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ProfileInfoRow(label = "Họ và tên", value = name)
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Số điện thoại", value = phoneNumber)
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Email", value = email)
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
        ProfileInfoRow(label = "Giới tính", value = if(gender) "Nam" else "Nữ")
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

        Spacer(modifier = Modifier.weight(1f))

        // Edit Information button
        Button(
            onClick = onClick,
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