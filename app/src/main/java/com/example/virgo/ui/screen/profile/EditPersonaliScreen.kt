package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPersonalScreen() {
    var name by remember { mutableStateOf("Trần Cường") }
    var gender by remember { mutableStateOf("Nam") }
    var phone by remember { mutableStateOf("0389 214 919") }
    var email by remember { mutableStateOf("abc@gmail.com") }
    var birthDate by remember { mutableStateOf("Ngày sinh") }
    var showDatePicker by remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

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
                        .clickable { }
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
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.avatar), contentDescription = null)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Thay đổi ảnh đại diện",
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).clickable{ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Họ và tên") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gender Selection
            Text(text = "Giới tính")
            Row {
                RadioButton(
                    selected = gender == "Nam",
                    onClick = { gender = "Nam" }
                )
                Text(text = "Nam", modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = gender == "Nữ",
                    onClick = { gender = "Nữ" }
                )
                Text(text = "Nữ", modifier = Modifier.align(Alignment.CenterVertically))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Field
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Số điện thoại") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Birthdate Field
            OutlinedTextField(
                value = birthDate,
                onValueChange = { /* No action needed */ },
                label = { Text("Ngày sinh") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                readOnly = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Handle update action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cập nhật thông tin")
            }
        }
    }

    if (showDatePicker) {
        var selectedDay by remember { mutableStateOf(1) }
        var selectedMonth by remember { mutableStateOf(1) }
        var selectedYear by remember { mutableStateOf(2023) }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .background(Color.White) // Set background color here
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Title and close button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Chỉnh sửa", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showDatePicker = false }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Cancel")
                        }
                    }

                    // Day, Month, Year Picker in the same row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Day Picker
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Ngày", fontSize = 14.sp)
                            NumberPicker(range = 1..31, onValueSelected = { selectedDay = it })
                        }

                        // Month Picker
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Tháng", fontSize = 14.sp)
                            NumberPicker(range = 1..12, onValueSelected = { selectedMonth = it })
                        }

                        // Year Picker
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Năm", fontSize = 14.sp)
                            NumberPicker(range = 1900..2100, onValueSelected = { selectedYear = it })
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Display selected date as a preview (optional)
                    Text(text = "Selected Date: $selectedDay/$selectedMonth/$selectedYear", fontSize = 16.sp)
                }
            },
            modifier = Modifier.heightIn(min = 500.dp) // Set default minimum height for the sheet
        ) {
            // Main content goes here (outside of the sheet)
        }
    }

}


@Composable
fun NumberPicker(range: IntRange, onValueSelected: (Int) -> Unit) {
    var selectedValue by remember { mutableIntStateOf(range.first) }

    LazyColumn(
        modifier = Modifier.height(150.dp) // Set height to limit the visible items
    ) {
        items(range.toList()) { number ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        selectedValue = number
                        onValueSelected(number)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    fontSize = if (number == selectedValue) 24.sp else 18.sp, // Highlight selected item
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Preview (showBackground = true)
@Composable
fun EditPerPreview(){
    EditPersonalScreen()
}
