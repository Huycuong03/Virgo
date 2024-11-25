package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.draw.clip
import com.example.virgo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentHistoryScreen(
    appointments: MutableList<Appointment>,
    onCancelAppointment: (Appointment) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Sắp đến", "Hoàn thành", "Đã hủy")
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = SnackbarHostState()
            )
        },
        topBar = {
            TopAppBar(
                title = { Text("Lịch sử đặt hẹn") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF1F5FB)),
                navigationIcon = {
                    IconButton(onClick = { /* Handle Back */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFF1F5FB))
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }


            when (selectedTabIndex) {
                0 -> AppointmentList(
                    appointments = appointments.filter { it.status == "Chưa hoàn thành" },
                    onCancelAppointment = { appointment ->
                        onCancelAppointment(appointment)
                        snackbarMessage = "Lịch hẹn đã được hủy thành công."
                        snackbarVisible = true
                        selectedTabIndex = 2
                    }
                )
                1 -> AppointmentList(
                    appointments = appointments.filter { it.status == "Hoàn thành" },
                    onCancelAppointment = null
                )
                2 -> AppointmentList(
                    appointments = appointments.filter { it.status == "Đã hủy" },
                    onCancelAppointment = null
                )
            }
        }


        if (snackbarVisible) {
            Snackbar(
                action = {
                    TextButton(onClick = { snackbarVisible = false }) {
                        Text("OK", color = Color.White)
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(snackbarMessage)
            }
        }
    }
}




@Composable
fun AppointmentList(
    appointments: List<Appointment>,
    onCancelAppointment: ((Appointment) -> Unit)?
) {
    if (appointments.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Date",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp),
                    tint = Color.LightGray
                )
                Text(
                    text = "Không có lịch hẹn nào.",
                    fontSize = 20.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(appointments) { appointment ->
                AppointmentCard(appointment = appointment, onCancelAppointment = onCancelAppointment)
            }
        }
    }
}


@Composable
fun AppointmentCard(
    appointment: Appointment,
    onCancelAppointment: ((Appointment) -> Unit)?
) {
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Xác nhận hủy lịch hẹn") },
            text = { Text("Bạn có chắc chắn muốn hủy lịch hẹn này?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onCancelAppointment?.invoke(appointment)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Hủy")
                }
            },
            containerColor = Color(0xFFF1F5FB)
        )
    }


    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_holder),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )


                Spacer(modifier = Modifier.width(16.dp))


                Text(
                    text = appointment.facilityName,
                    style = MaterialTheme.typography.titleMedium
                )
            }


            Spacer(modifier = Modifier.height(8.dp))


            Row {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ngày: ${appointment.date}",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${appointment.fromHour} - ${appointment.toHour}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))


            if (onCancelAppointment != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hủy lịch hẹn",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hủy",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppointmentHistoryPreview() {
    val sampleAppointments = remember {
        mutableStateListOf(
            Appointment(
                id = "1",
                userId = "123",
                facilityId = "f1",
                facilityName = "Phòng khám Đa khoa Quốc tế Golden Healthcare",
                date = "25/11/2024",
                fromHour = "08:00",
                toHour = "08:30",
                orderNumber = "001",
                status = "Chưa hoàn thành",
                payment = Payment(
                    totalPrice = 500000.0,
                    timestamp = "23/11/2024 15:45"
                )
            ),
            Appointment(
                id = "2",
                userId = "124",
                facilityId = "f2",
                facilityName = "Bệnh viện Đại học Y Dược TP.HCM",
                date = "26/11/2024",
                fromHour = "10:00",
                toHour = "10:30",
                orderNumber = "002",
                status = "Hoàn thành",
                payment = Payment(
                    totalPrice = 300000.0,
                    timestamp = "24/11/2024 14:00"
                )
            )
        )
    }


    AppointmentHistoryScreen(
        appointments = sampleAppointments,
        onCancelAppointment = { appointment ->
            val index = sampleAppointments.indexOfFirst { it.id == appointment.id }
            if (index != -1) {
                val updated = sampleAppointments[index].copy(status = "Đã hủy")
                sampleAppointments[index] = updated
            }
        }
    )
}


data class Appointment(
    val id: String,
    val userId: String,
    val facilityId: String,
    val facilityName: String,
    val date: String,
    val fromHour: String,
    val toHour: String,
    val orderNumber: String,
    var status: String,
    val payment: Payment
)


data class Payment(
    val totalPrice: Double,
    val timestamp: String
)


