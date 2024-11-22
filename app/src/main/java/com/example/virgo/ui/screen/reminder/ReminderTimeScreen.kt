package com.example.virgo.ui.screen.reminder

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTimeScreen() {
    val currentDate = LocalDate.now()
    val startDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val numberOfDays = 20
    val endDate = calculateEndDate(startDate, numberOfDays)
    val timeMap = remember { mutableStateMapOf("Sáng" to "07:30", "Trưa" to "12:00", "Chiều" to "15:00", "Tối" to "18:00") }
    var isReminderOn by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedLabel by remember { mutableStateOf("") }

    // Root Box to position buttons at the bottom
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top AppBar
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Hẹn giờ nhắc",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1565C0))
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Đơn thuốc ngày $startDate - $endDate",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Reminder Time Rows
                timeMap.forEach { (label, time) ->
                    TimePickerRow(
                        label = label,
                        time = time,
                        onTimeClick = {
                            selectedLabel = label
                            showTimePicker = true
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Toggle Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Bật thông báo", style = TextStyle(fontSize = 16.sp))
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isReminderOn,
                        onCheckedChange = { isReminderOn = it }
                    )
                }
            }
        }

        // Bottom Buttons: "Trở lại" and "Hoàn thành"
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Handle "Trở lại" action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text("Trở lại", color = Color.White)
            }

            Button(
                onClick = { /* Handle "Hoàn thành" action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Hoàn thành", color = Color.White)
            }
        }
    }

    // Time Picker Dialog positioned at the bottom
    if (showTimePicker) {
        val initialTime = timeMap[selectedLabel] ?: "00:00"
        TimePickerDialog(
            initialTime = initialTime,
            onTimeSelected = { newTime ->
                timeMap[selectedLabel] = newTime
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}


// Calculate End Date
fun calculateEndDate(startDate: String, numberOfDays: Int): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = sdf.parse(startDate)
    val calendar = java.util.Calendar.getInstance()
    date?.let {
        calendar.time = it
        calendar.add(java.util.Calendar.DAY_OF_YEAR, numberOfDays)
    }
    return sdf.format(calendar.time)
}

// TimePickerRow
@Composable
fun TimePickerRow(label: String, time: String, onTimeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 16.sp)
        }
        Text(
            text = time,
            style = TextStyle(fontSize = 16.sp, color = Color.Blue),
            modifier = Modifier.clickable { onTimeClick() }
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerDialog(
    initialTime: String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val hours = (0..23).toList()
    val minutes = (0..59).toList()

    // Extract initial hour and minute from initialTime string
    val (initialHour, initialMinute) = initialTime.split(":").map { it.toInt() }

    var selectedHour by remember { mutableIntStateOf(initialHour) }
    var selectedMinute by remember { mutableIntStateOf(initialMinute) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Card(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)  // Set the size to 30% of the screen height
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header with delete icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Text(
                    text = "Set Time",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Time Picker Row
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour Picker
                    NumberPicker(
                        value = selectedHour,
                        range = hours,
                        onValueChange = { selectedHour = it }
                    )
                    Text(text = ":", style = TextStyle(fontSize = 32.sp))
                    // Minute Picker
                    NumberPicker(
                        value = selectedMinute,
                        range = minutes,
                        onValueChange = { selectedMinute = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    onTimeSelected(formattedTime)
                }) {
                    Text(text = "OK")
                }
            }
        }
    }
}


// NumberPicker Composable (You can use libraries like WheelPicker for a better design)
@Composable
fun NumberPicker(value: Int, range: List<Int>, onValueChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp, 150.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(range) { number ->
                Text(
                    text = number.toString(),
                    fontSize = 24.sp,
                    color = if (number == value) Color.Black else Color.Gray,
                    modifier = Modifier
                        .clickable { onValueChange(number) }
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderTimePreview(){
    ReminderTimeScreen()
}
