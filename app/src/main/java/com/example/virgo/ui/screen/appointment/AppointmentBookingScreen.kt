package com.example.virgo.ui.screen.appointment

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.lib.Session
import com.example.virgo.route.appointment.AppointmentHistoryRoute
import com.example.virgo.ui.screen.lib.AppointmentBookingDialog
import com.example.virgo.viewModel.appointment.AppointmentBookingViewModel
import java.time.LocalDate
import androidx.compose.ui.platform.LocalContext
import com.example.virgo.model.appointment.Facility
import com.example.virgo.model.appointment.OperatingHour

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentBookingScreen(facilityId: String, navController: NavController) {
    val viewModel : AppointmentBookingViewModel = viewModel()
    val scrollState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }
    val reason = viewModel.reason.value
    var selectedDate = viewModel.selectedDate.value
    var selectedSession = viewModel.selectedSession.value
    val facility = viewModel.facility.value
    val user = viewModel.user.value
    val context = LocalContext.current
    var validationMessage by remember { mutableStateOf("") }

    LaunchedEffect(key1 = facilityId) {
        viewModel.init(facilityId)
    }

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
                    IconButton(onClick = { navController.popBackStack() }) {
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
                    user.avatarImage?.let {
                        SectionWithAvatarAndDetails(
                            title = "Bệnh nhân",
                            image = it,
                            details = listOf(
                                user.name to "name",
                                user.email to "email",
                                user.phoneNumber to "phone"
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    facility.avatarImage?.let {
                        SectionWithAvatarAndDetails(
                            title = "Bệnh viện",
                            image = it,
                            details = listOf(
                                facility.name to "name",
                                facility.address.toString() to "location"
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                CardBox(
                    title = "Ngày giờ hẹn khám",
                    initialDate = selectedDate,
                    initialTime = selectedSession,
                    onDateTimeSelected = { date, session ->
                        selectedDate = date
                        selectedSession = session
                        viewModel.onDateTimeSelected(date, session)
                    },
                    onEditClick = { showDialog = true },
                    facility
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ){
                        BasicTextField(
                            value = reason,
                            onValueChange = { viewModel.onChangeReason(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            decorationBox = { innerTextField ->
                                if (reason.isEmpty()) {
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Phí tư vấn: 200.000đ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (validationMessage.isNotEmpty()) {
                    Text(
                        text = validationMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.createAppointment(
                            onSuccess = {
                                navController.popBackStack()
                                navController.navigate(AppointmentHistoryRoute)
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to create appointment", Toast.LENGTH_SHORT).show()
                            },
                            onValidationFailure = { message ->
                                validationMessage = message
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff))
                ) {
                    Text("Xác nhận đặt hẹn", color = Color.White)
                }
            }
        }

        if (showDialog) {
            AppointmentBookingDialog(
                onDismiss = { showDialog = false },
                onConfirm = { date, session ->
                    viewModel.onDateTimeSelected(date, session)
                    showDialog = false
                },
                facility.operatingHours
            )
        }
    }
}
@Composable
fun CardBox(
    title: String,
    initialDate: LocalDate?,
    initialTime: Session,
    onDateTimeSelected: (LocalDate, Session) -> Unit,
    onEditClick: () -> Unit,
    facility: Facility
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AppointmentBookingDialog(
            onDismiss = { showDialog = false },
            onConfirm = { date, session ->
                onDateTimeSelected(date, session)
                showDialog = false
            },
            facility.operatingHours
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
                        text = if (initialTime.isNull()) "Chọn giờ" else initialTime.toString(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                }
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
    image: String,
    details: List<Pair<String?, String>>
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
                AsyncImage(
                    model = (stringResource(id = R.string.github_page) + "/drawable/" + (image)),
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
                            if (text != null) {
                                Text(
                                    text = text,
                                    fontWeight = if (type == "name") FontWeight.Bold else FontWeight.Normal,
                                    fontSize = if (type == "name") 16.sp else 14.sp,
                                    color = if (type == "name") Color.Black else Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)

                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}