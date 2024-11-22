package com.example.virgo.ui.screen.reminder

import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderHome() {
    val activeReminders = remember { mutableStateListOf("Reminder 1", "Reminder 2") }
    val inactiveReminders = remember { mutableStateListOf("Reminder A", "Reminder B") }

    var showActive by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reminder") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFADD8E6)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFADD8E6)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { /* Navigate to Reminder Home Page */ }) {
                        Text("Reminder Home")
                    }
                    Button(onClick = { /* Add Reminder Logic Here */ }) {
                        Text("Add Reminder")
                    }
                }
            }
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
                Button(onClick = { showActive = true }) {
                    Text("Activate")
                }
                Button(onClick = { showActive = false }) {
                    Text("Inactivate")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showActive) {
                Text(text = "Active Reminders", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(activeReminders) { reminder ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .padding(16.dp)
                        ) {
                            Text(text = reminder)
                        }
                    }
                }
            } else {
                Text(text = "Inactive Reminders", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(inactiveReminders) { reminder ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .padding(16.dp)
                        ) {
                            Text(text = reminder)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderHomePreview() {
    ReminderHome()
}


