package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.model.lib.Session
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun AppointmentBookingDialog(onDismiss: () -> Unit, onConfirm: (LocalDate, Session) -> Unit) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedSession by remember { mutableStateOf<Session?>(null) }
    var currentMonth by remember { mutableStateOf(LocalDate.of(2024, 11, 1)) } // Set to November 2024

    val today = LocalDate.now()
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.withDayOfMonth(1).dayOfWeek.value % 7 // Adjust to 0-based index


    val previousMonth = currentMonth.minusMonths(1)
    val daysInPreviousMonth = previousMonth.lengthOfMonth()
    val leadingDays = (daysInPreviousMonth - firstDayOfWeek + 1..daysInPreviousMonth).toList()

    val currentMonthDays = (1..daysInMonth).toList()

    // Dates for the next month to fill the trailing empty slots if needed
    val trailingDays = (1..(7 - (leadingDays.size + currentMonthDays.size) % 7) % 7).toList()

    // Combine all days into a single list with a flag to indicate if they belong to the current month
    val calendarDays = leadingDays.map { it to false } + currentMonthDays.map { it to true } + trailingDays.map { it to false }

    // Define weekday and weekend operating hours
    val weekdayHours = listOf(
        Session(7, 9),
        Session(9, 11),
        Session(13, 15),
        Session(15, 18)
    )
    val weekendHours = listOf(
        Session(7, 9),
        Session(9, 11),
        Session(13, 15),
        Session(15, 17)
    )

    // Determine available time slots based on the selected date
    val availableSessions = when (selectedDate?.dayOfWeek) {
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

                // Day of Week Labels
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
                                            else -> Color.Gray.copy(alpha = 0.5f) // Faded for non-current month days
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

                if (availableSessions.isEmpty()) {
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
                        availableSessions.chunked(2).forEach { rowSlots ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowSlots.forEach { slot ->
                                    val session = Session(slot.fromHour, slot.toHour)
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(
                                                if (selectedSession == session) Color(0xFFD0E8FF) else Color.LightGray
                                            )
                                            .clickable { selectedSession = session },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = session.toString(),
                                            color = if (selectedSession == session) Color(0xFF007BFF) else Color.Black,
                                            fontWeight = if (selectedSession == session) FontWeight.Bold else FontWeight.Normal,
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

            // Confirm Button
            Button(
                onClick = {
                    selectedDate?.let { date ->
                        selectedSession?.let { Session ->
                            onConfirm(date, Session)
                        }
                    }
                },
                enabled = selectedDate != null && selectedSession != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDate != null && selectedSession != null) Color(0xFF007BFF) else Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tiếp tục", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}