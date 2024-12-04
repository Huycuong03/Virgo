package com.example.virgo.ui.screen.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.route.profile.PersonalInformationRoute
import com.example.virgo.viewModel.profileTest.ProfileViewModel

@Composable
fun EditPersonalScreen(navController: NavController) {
    val viewModel : ProfileViewModel = viewModel()
    val user = viewModel.user.value
    var validationMessage by remember { mutableStateOf("") }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        viewModel.uploadAvatarImage(it)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        singlePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = user.avatarImage,
                    contentDescription = "Image Description",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Thay đổi ảnh đại diện",
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = user.name?:"",
                onValueChange = {viewModel.onChangeName(it) },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gender Selection
            Text(text = "Giới tính")
            Row {
                RadioButton(
                    selected = user.gender == true,
                    onClick = { viewModel.onChangeGender(true) }
                )
                Text(text = "Nam", modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = user.gender == false,
                    onClick = { viewModel.onChangeGender(false) }
                )
                Text(text = "Nữ", modifier = Modifier.align(Alignment.CenterVertically))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Field
            OutlinedTextField(
                value = user.phoneNumber?:"",
                onValueChange = {},
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = user.email?:"",
                onValueChange = { viewModel.onChangeEmail(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (validationMessage.isNotEmpty()) {
                Text(
                    text = validationMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.updateUser(
                        onSuccess = { navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate(PersonalInformationRoute)
                                    },
                        onFailure = { Toast.makeText(navController.context, "Failed to create appointment", Toast.LENGTH_SHORT).show()},
                        onValidationFailure = { message ->
                            validationMessage = message
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cập nhật thông tin")
            }
        }
    }

}