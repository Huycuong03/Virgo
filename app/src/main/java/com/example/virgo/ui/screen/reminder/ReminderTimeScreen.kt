package com.example.virgo.ui.screen.reminder

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.virgo.model.lib.Alarm
import com.example.virgo.model.lib.Reminder
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.reminder.ReminderListRoute
import com.example.virgo.viewModel.reminder.ReminderTimeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTimeScreen(rmd: Reminder, navController: NavController) {
    val viewModel: ReminderTimeViewModel = viewModel()
    val alarms = viewModel.alarms.value
    val reminder = viewModel.reminder.value


    LaunchedEffect(key1 = rmd) {
        viewModel.init(rmd, navController.context.applicationContext)
    }

//    var showTimePicker by remember { mutableStateOf(false) }


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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    // Add Icon
                    IconButton(onClick = {
                        viewModel.addNewAlarm()
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1565C0))
            )


            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = reminder.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Reminder Time Rows
                alarms.forEachIndexed() { index, alarm ->
                    TimePickerRow(
                        label = "Bao thuc ${index + 1}",
                        alarm = alarm,
                        onDelete = {
                            viewModel.removeAlarm(alarm)
                        },
                        onChangeTime = {viewModel.onUpdateAlarm(alarm, it)}
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
                        checked = reminder.isActive,
                        onCheckedChange = { viewModel.onSwitchActive(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // "Trước ăn" button
                    Button(
                        onClick = { viewModel.onSwitchNote(true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (reminder.note) Color.Blue else Color.Gray,
                            contentColor = if (reminder.note) Color.White else Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Trước ăn")
                    }

                    // "Sau ăn" button
                    Button(
                        onClick = { viewModel.onSwitchNote(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!reminder.note) Color.Blue else Color.Gray,
                            contentColor = if (!reminder.note) Color.White else Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Sau ăn")
                    }
                }
            }
        }

        // Bottom Buttons: "Trở lại" and "Hoàn thành"
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {viewModel.onSave(navController.context.applicationContext){
                    navController.navigate(ReminderListRoute)
                }
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            ) {
                Text("Hoàn thành", color = Color.White)
            }
        }
    }

    // Time Picker Dialog positioned at the bottom
//    if (showTimePicker) {
//        TimePickerDialog(
//            onTimeSelected = {
//                viewModel.onUpdateAlarm(it)
//                showTimePicker = false
//            },
//            onDismiss = { showTimePicker = false }
//        )
//    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerRow(
    label: String,
    alarm: Alarm,
    onDelete: () -> Unit,
    onChangeTime: (Alarm) -> Unit
) {
    var hour by remember {
        mutableIntStateOf(alarm.hour)
    }
    var min by remember {
        mutableIntStateOf(alarm.min)
    }
    var enabled by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { enabled = !enabled},
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Row {
            TextField(modifier = Modifier.width(70.dp), value = if(hour != -1) hour.toString() else "",
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        hour = it.toInt()
                        onChangeTime(alarm.copy(hour = it.toInt()))
                    } else {
                        hour = -1
                    } },
                enabled = enabled )
            Text(text = ":")
            TextField(modifier = Modifier.width(70.dp),value = if(min != -1) min.toString() else "",
                onValueChange = {val newValue = it.toIntOrNull() // Converts to Int or returns null if input is invalid
                    if (newValue != null) {
                        min = it.toInt()
                        onChangeTime(alarm.copy(min = it.toInt()))
                    } else {
                        min = -1
                    }}, enabled = enabled )
        }
        IconButton(onClick = { onDelete() }) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
        }
    }
}

// TimePickerRow
//@SuppressLint("DefaultLocale")
//@Composable
//fun TimePickerDialog(
//    initialTime: Alarm = Alarm(),
//    onTimeSelected: (Alarm) -> Unit,
//    onDismiss: () -> Unit
//) {
//    val hours = (0..23).toList()
//    val minutes = (0..59).toList()
//
//
//    var tempHour by remember { mutableIntStateOf(initialTime.hour) }
//    var tempMinute by remember { mutableIntStateOf(initialTime.min) }
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//        Card(
//            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//                .padding(16.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Header with close icon
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    IconButton(onClick = { onDismiss() }) {
//                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.Gray)
//                    }
//                }
//
//                Text(
//                    text = "Set Time",
//                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                // Time Picker Row
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    // Hour Picker
//                    NumberPickerWithLines(
//                        value = tempHour,
//                        range = hours,
//                        onValueChange = { tempHour = it }
//                    )
//                    Text(text = ":", style = TextStyle(fontSize = 32.sp))
//                    // Minute Picker
//                    NumberPickerWithLines(
//                        value = tempMinute,
//                        range = minutes,
//                        onValueChange = { tempMinute = it }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(onClick = {
//                    onTimeSelected(Alarm(tempHour, tempMinute))
//                }) {
//                    Text(text = "OK")
//                }
//            }
//        }
//    }
//}

//@Composable
//fun NumberPickerWithLines(
//    value: Int,
//    range: List<Int>,
//    onValueChange: (Int) -> Unit,
//    highlightColor: Color = Color.Blue
//) {
//    val listState = rememberLazyListState()
//
//    // Scroll to the selected value initially
//    LaunchedEffect(value) {
//        listState.scrollToItem(value)
//    }
//
//    Box(
//        modifier = Modifier
//            .size(80.dp, 150.dp)
//            .background(Color.LightGray),
//        contentAlignment = Alignment.Center
//    ) {
//        LazyColumn(
//            state = listState,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(range) { number ->
//                Text(
//                    text = String.format("%02d", number),
//                    fontSize = 24.sp,
//                    color = if (number == value) highlightColor else Color.Black,
//                    fontWeight = if (number == value) FontWeight.Bold else FontWeight.Normal,
//                    modifier = Modifier
//                        .clickable {
//                            onValueChange(number)
//                            // Launch coroutine to scroll
//                            CoroutineScope(Dispatchers.Main).launch {
//                                listState.scrollToItem(number)
//                            }
//                        }
//                        .padding(4.dp)
//                )
//            }
//        }
//
//        // Highlight the middle selection
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(40.dp)
//                .background(Color.Transparent)
//                .border(BorderStroke(2.dp, highlightColor), shape = RectangleShape)
//        )
//    }
//}


