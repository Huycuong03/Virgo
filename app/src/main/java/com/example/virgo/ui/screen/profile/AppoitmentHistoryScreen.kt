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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.model.appointment.Appointment
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.appointment.AppointmentHistoryRoute
import com.example.virgo.viewModel.appointment.AppointmentHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentHistoryScreen(
    navController: NavController
) {
    val viewModel: AppointmentHistoryViewModel = viewModel()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Sắp đến", "Hoàn thành", "Đã hủy")
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val appointments = viewModel.appointmentList.value
    val onCancelAppointment: (Appointment) -> Unit = { appointment ->
        viewModel.cancelAppointment(appointment)
        snackbarMessage = "Lịch hẹn đã được hủy thành công."
        snackbarVisible = true
    }

    LaunchedEffect(key1 = "") {
        viewModel.init()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = SnackbarHostState()) },
        topBar = {
            TopAppBar(
                title = { Text("Lịch sử đặt hẹn") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF1F5FB)),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Home")
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

            val filteredAppointments = when (selectedTabIndex) {
                0 -> appointments.filter { it.status == "coming" }
                1 -> appointments.filter { it.status == "Hoàn thành" }
                2 -> appointments.filter { it.status == "Đã hủy" }
                else -> emptyList()
            }

            AppointmentList(
                appointments = filteredAppointments,
                onCancelAppointment = onCancelAppointment,
                showCancelOption = selectedTabIndex == 0
            )
        }

        // Snackbar
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
    onCancelAppointment: ((Appointment) -> Unit)?,
    showCancelOption: Boolean
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
                    contentDescription = "No appointments",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(16.dp),
                    tint = Color.LightGray
                )
                Text(
                    text = "Không có lịch hẹn nào.",
                    fontSize = 20.sp,
                    color = Color.LightGray
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
                AppointmentCard(
                    appointment = appointment,
                    onCancelAppointment = onCancelAppointment.takeIf { showCancelOption }
                )
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
            }
        )
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    text = appointment.facility?.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp),
                    tint = Color.Gray
                )
                Column {
                    Text(
                        text = "Ngày: ${appointment.getFormatedDate()}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Giờ: ${appointment.session.toString()}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Số thứ tự: ${appointment.orderNumber?: "Đang chờ"}",
                fontSize = 16.sp,
                color = Color.Black
            )

            if (onCancelAppointment != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Hủy lịch hẹn", color = Color.White)
                }
            }
        }
    }
}
