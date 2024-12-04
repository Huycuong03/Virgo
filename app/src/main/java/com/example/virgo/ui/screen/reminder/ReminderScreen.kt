package com.example.virgo.ui.screen.reminder

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.lib.Reminder
import com.example.virgo.route.reminder.SearchToReminderRoute
import com.example.virgo.sqlite.ReminderDatabaseHelper
import com.example.virgo.viewModel.reminder.ReminderHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderListScreen(navController: NavController) {
    val viewModel: ReminderHomeViewModel = viewModel()

    val activeReminders = viewModel.activeReminders.value
    val inactiveReminders = viewModel.inactiveReminders.value
    Log.d("Screen", activeReminders.size.toString())
    Log.d("Screen", inactiveReminders.size.toString())

    var showDialog by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<Reminder?>(null) }
    var showActive by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.loadReminders(navController.context.applicationContext)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reminder") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFADD8E6)
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(SearchToReminderRoute) }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { showActive = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showActive) Color.Blue else Color.Gray, // Highlight when active
                        contentColor = Color.White
                    )
                ) {
                    Text("Activate")
                }

                Button(
                    onClick = { showActive = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!showActive) Color.Blue else Color.Gray, // Highlight when inactive
                        contentColor = Color.White
                    )
                ) {
                    Text("Inactivate")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showActive) {
                LazyColumn {
                    items(activeReminders) { reminder ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .padding(16.dp)
                                .clickable {
                                    selectedReminder = reminder
                                    showDialog = true
                                }
                        ) {
                            // Use a Row to place Text on the left and IconButton on the right
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = reminder.name,
                                    modifier = Modifier.weight(1f)  // Ensures Text takes available space
                                )
                                IconButton(
                                    onClick = { viewModel.deleteReminder(reminder, navController.context.applicationContext) }
                                ) {
                                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(inactiveReminders) { reminder ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .padding(16.dp)
                                .clickable {
                                    selectedReminder = reminder
                                    showDialog = true
                                }
                        ) {
                            // Use a Row to place Text on the left and IconButton on the right
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = reminder.name,
                                    modifier = Modifier.weight(1f)  // Ensures Text takes available space
                                )
                                IconButton(
                                    onClick = { viewModel.deleteReminder(reminder, navController.context.applicationContext) }
                                ) {
                                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog && selectedReminder != null) {
        ReminderDialog(reminder = selectedReminder!!) {
            // Handle dismissing the dialog, you can close the dialog here
            showDialog = false
        }
    }
}

@Composable
fun ReminderDialog(reminder: Reminder, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Reminder Details") },
        text = {
            Column {
                Text("Name: ${reminder.name}")
                Text("Date Started: ${reminder.dateCreated} ")
                Text("Duration: ${reminder.duration} days")
                Text("Skip: ${reminder.skip} days")
                Text("Alarm: ${reminder.alarms.joinToString("\n")}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}




