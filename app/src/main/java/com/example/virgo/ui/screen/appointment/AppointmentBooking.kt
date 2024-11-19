package com.example.virgo.ui.screen.appointment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentConfirmationScreen() {
    var reason by remember { mutableStateOf(TextFieldValue()) }
    var patientType by remember { mutableStateOf("new") }
    val scrollState = rememberLazyListState()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTimeSlot by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) } // Control bottom sheet visibility

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Xác nhận đặt lịch hẹn",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back action */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF1F5FB)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F5FB))
                .padding(paddingValues)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SectionWithAvatarAndDetails(
                        title = "Bệnh nhân",
                        imageRes = R.drawable.image_holder,
                        details = listOf(
                            "Thi Nguyễn Văn" to "name",
                            "a2k57tp@gmail.com" to "email",
                            "+84336386034" to "phone"
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionWithAvatarAndDetails(
                        title = "Bệnh viện",
                        imageRes = R.drawable.image_holder,
                        details = listOf(
                            "Phòng khám Đa khoa Quốc tế Golden Healthcare" to "name",
                            "Đa khoa" to "department",
                            "37 Hoàng Hoa Thám, P. 13, Q. Tân Bình, TP. HCM" to "location"
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CardBox(
                    title = "Ngày giờ hẹn khám",
                    type = "Tư vấn trực tiếp",
                    initialDate = selectedDate,
                    initialTime = selectedTimeSlot,
                    onDateTimeSelected = { date, timeSlot ->
                        selectedDate = date
                        selectedTimeSlot = timeSlot
                    },
                    onEditClick = { showDialog = true }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "CÂU HỎI KHẢO SÁT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Bạn đã sử dụng dịch vụ của phòng khám này chưa?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = patientType == "new",
                            onClick = { patientType = "new" }
                        )
                        Text("Bệnh nhân mới")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(
                            selected = patientType == "existing",
                            onClick = { patientType = "existing" }
                        )
                        Text("Bệnh nhân cũ")
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ){
                        BasicTextField(
                            value = reason,
                            onValueChange = { reason = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp),
                            decorationBox = { innerTextField ->
                                if (reason.text.isEmpty()) {
                                    Text(
                                        text = "Nêu lí do, triệu chứng để buổi hẹn khám của bạn được chuẩn bị tốt hơn",
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                    
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Phí tư vấn: 200.000 đ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Confirm Action */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
                ) {
                    Text("Xác nhận đặt hẹn", color = Color.White)
                }
            }
        }
        if (showDialog) {
            AppointmentBookingDialog(
                onDismiss = { showDialog = false },
                onConfirm = { date, timeSlot ->
                    selectedDate = date
                    selectedTimeSlot = timeSlot
                    showDialog = false
                }
            )
        }
    }
}
@Composable
fun CardBox(
    title: String,
    type: String,
    initialDate: LocalDate?,
    initialTime: String?,
    onDateTimeSelected: (LocalDate, String) -> Unit,
    onEditClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AppointmentBookingDialog(
            onDismiss = { showDialog = false },
            onConfirm = { date, timeSlot ->
                onDateTimeSelected(date, timeSlot)
                showDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = initialDate?.toString() ?: "Chọn ngày",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = initialTime ?: "Chọn giờ",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = type,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF007BFF),
                    modifier = Modifier
                        .background(
                            color = Color(0xFFD0E8FF),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Date and Time",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}



@Composable
fun SectionWithAvatarAndDetails(
    title: String,
    imageRes: Int,
    details: List<Pair<String, String?>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    details.forEach { (text, type) ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (type == "email" || type =="phone"|| type =="location") {
                                when (type) {
                                    "email" -> Icon(
                                        imageVector = Icons.Filled.Email,
                                        contentDescription = "Email",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Gray
                                    )
                                    "phone" -> Icon(
                                        imageVector = Icons.Filled.Call,
                                        contentDescription = "Phone",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Gray
                                    )
                                    "location" -> Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = "Location",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                                Text(
                                    text = text,
                                    fontWeight = if (type.equals("name") || type.equals("department")) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = if (type.equals("name")|| type.equals("department")) 16.sp else 14.sp,
                                    color = if (type.equals("name")|| type.equals("department")) Color.Black else Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)

                                )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    AppointmentConfirmationScreen()
}
///
