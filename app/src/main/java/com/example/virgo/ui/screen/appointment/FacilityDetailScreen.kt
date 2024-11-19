package com.example.virgo.ui.screen.appointment

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import java.time.DayOfWeek
import java.time.LocalDate
import androidx.compose.material.icons.automirrored.filled.ArrowForward

data class TimeSlot(val fromHour: Int, val toHour: Int)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FacilityDetail() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val scrollState = rememberLazyListState()
    var scrollOffset by remember { mutableFloatStateOf(0f) }
    var showBookingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        snapshotFlow {
            scrollState.firstVisibleItemScrollOffset
        }.collect { offset ->
            scrollOffset = if (scrollState.firstVisibleItemIndex > 0) 1f else offset / 200f
        }
    }
    val titleOpacity by animateFloatAsState(
        targetValue = scrollOffset.coerceIn(0f, 1f), label = ""
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Trung tâm Xét nghiệm Sài Gòn",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = titleOpacity)
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_holder),
                        contentDescription = "Cover Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.image_holder),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White, CircleShape)
                            .align(Alignment.BottomStart)
                            .offset(x = 1.dp, y = 1.dp)
                    )
                }
            }

            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(top = 48.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Trung tâm Xét nghiệm Sài Gòn",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "40 Tân Khai, Phường 4, Quận 11, TP.HCM",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F5FB))
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TabButton(
                        text = "Thông tin cơ bản",
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 }
                    )
                    TabButton(
                        text = "Đánh giá",
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    item { BasicInfoContent() }
                }
                1 -> {
                    item { ReviewContent() }
                }
            }

            if (selectedTab == 0) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showBookingDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Đặt lịch hẹn",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (showBookingDialog) {
            AppointmentBookingDialog(
                onDismiss = { showBookingDialog = false },
                onConfirm = { date, timeSlot ->
                    showBookingDialog = false
                }
            )
        }
    }
}

@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onClick() }
            .background(
                color = if (selected) Color(0xFF007BFF) else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}



@Composable
fun BasicInfoContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Giờ làm việc", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Thứ Hai - Chủ Nhật: 07:00 - 18:00", fontSize = 16.sp)

        ExpandableSection("Thông tin bệnh viện") {
            Text(
                "Trung tâm Xét nghiệm Y khoa Medilab được thành lập vào tháng 9/2019 với chuỗi hệ thống 7 chi nhánh trải đều khắp các tỉnh thành. Trung tâm hiện đang triển khai các gói dịch vụ kiểm tra sức khỏe toàn diện, chẩn đoán hình ảnh cũng như các hạng mục xét nghiệm từ đơn giản đến chuyên sâu.",
                fontSize = 16.sp
            )
        }

        ExpandableSection("Chuyên khoa") {
            val departments = listOf(
                "Đa khoa",
                "Chẩn đoán hình ảnh"

            )
            Column {
                departments.forEach { service ->
                    Text(service, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        ExpandableSection("Cơ sở vật chất") {
            val csvc = listOf(
                "Phòng xét nghiệm",
                "Máy xét nghiệm miễn dịch tự động",
                "Máy xét nghiệm huyết học",
                "Giường bệnh"
            )
            Column {
                csvc.forEach { service ->
                    Text(service, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        ExpandableSection("Dịch vụ") {
            val services = listOf(
                "Gói xét nghiệm tổng quát - 2,500,000đ",
                "Gói xét nghiệm dị ứng - giun sán - 2,820,000đ",
                "Gói xét nghiệm bệnh lây qua đường tình dục - 150,000đ"
            )
            Column {
                services.forEach { service ->
                    Text(service, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        ExpandableSection("Hướng dẫn đặt lịch khám") {
            Text(
                "1. Chọn gói khám\n2. Đặt lịch hẹn\n3. Thanh toán\n4. Nhận xác nhận lịch hẹn",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp)
        )
        if (expanded) {
            content()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ReviewContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Đánh giá", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Chưa có đánh giá nào.", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun AppointmentBookingDialog(onDismiss: () -> Unit, onConfirm: (LocalDate, String) -> Unit) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTimeSlot by remember { mutableStateOf<String?>(null) }
    var currentMonth by remember { mutableStateOf(LocalDate.of(2024, 11, 1)) }

    val today = LocalDate.now()
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.withDayOfMonth(1).dayOfWeek.value - 1

    val previousMonth = currentMonth.minusMonths(1)
    val daysInPreviousMonth = previousMonth.lengthOfMonth()
    val leadingDays = (daysInPreviousMonth - firstDayOfWeek + 1..daysInPreviousMonth).toList()

    val currentMonthDays = (1..daysInMonth).toList()

    val trailingDays = (1..(7 - (leadingDays.size + currentMonthDays.size) % 7) % 7).toList()

    val calendarDays = leadingDays.map { it to false } + currentMonthDays.map { it to true } + trailingDays.map { it to false }

    val weekdayHours = listOf(
        TimeSlot(7, 9),
        TimeSlot(9, 11),
        TimeSlot(13, 15),
        TimeSlot(15, 18)
    )
    val weekendHours = listOf(
        TimeSlot(7, 9),
        TimeSlot(9, 11),
        TimeSlot(13, 15),
        TimeSlot(15, 17)
    )
    val availableTimeSlots = when (selectedDate?.dayOfWeek) {
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY -> weekdayHours
        DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> weekendHours
        else -> emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .clickable(enabled = false) {},
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Ngày giờ hẹn khám",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { if (currentMonth.isAfter(today.withDayOfMonth(1))) currentMonth = currentMonth.minusMonths(1) },
                        enabled = currentMonth.isAfter(today.withDayOfMonth(1))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous Month",
                            tint = if (currentMonth.isAfter(today.withDayOfMonth(1))) Color.Black else Color.Gray
                        )
                    }
                    Text(
                        "Tháng ${currentMonth.monthValue} ${currentMonth.year}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month", tint = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach { day ->
                        Text(day, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column {
                    calendarDays.chunked(7).forEach { week ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            week.forEach { (day, isCurrentMonth) ->
                                val date = if (isCurrentMonth) currentMonth.withDayOfMonth(day) else null
                                val isAvailable = date != null && date >= today.plusDays(1)
                                val isSelected = selectedDate == date

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .padding(4.dp)
                                        .background(
                                            if (isSelected) Color(0xFFD0E8FF) else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable(enabled = isAvailable) { selectedDate = date },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        fontWeight = if (isAvailable && isCurrentMonth) FontWeight.Bold else FontWeight.Normal,
                                        color = when {
                                            isSelected -> Color(0xFF007BFF)
                                            isCurrentMonth -> Color.Black
                                            else -> Color.Gray.copy(alpha = 0.5f)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    "Vui lòng chọn thời gian trống",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                if (availableTimeSlots.isEmpty()) {
                    Text(
                        "Vui lòng chọn ngày để xem các thời gian trống",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        availableTimeSlots.chunked(2).forEach { rowSlots ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowSlots.forEach { slot ->
                                    val timeSlotText = "${slot.fromHour}:00 - ${slot.toHour}:00"
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(
                                                if (selectedTimeSlot == timeSlotText) Color(0xFFD0E8FF) else Color.LightGray
                                            )
                                            .clickable { selectedTimeSlot = timeSlotText },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = timeSlotText,
                                            color = if (selectedTimeSlot == timeSlotText) Color(0xFF007BFF) else Color.Black,
                                            fontWeight = if (selectedTimeSlot == timeSlotText) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedDate?.let { date ->
                        selectedTimeSlot?.let { timeSlot ->
                            onConfirm(date, timeSlot)
                        }
                    }
                },
                enabled = selectedDate != null && selectedTimeSlot != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDate != null && selectedTimeSlot != null) Color(0xFF007BFF) else Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tiếp tục", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewClinicAndHospitalDetail() {
    FacilityDetail()
}
///