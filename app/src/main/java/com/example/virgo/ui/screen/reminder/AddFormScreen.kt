package com.example.virgo.ui.screen.reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import java.time.LocalDate

// Main form screen with reminders
@Composable
fun AddFormScreen(
    reminders: List<Product>,
    onAddReminder: () -> Unit,
    onSchedule: () -> Unit,
    onDelete: (Product) -> Unit
) {
    var showDosageDialog by remember { mutableStateOf(false) }
    var selectedReminder by remember { mutableStateOf<Product?>(null) }

    // Define the functions for dismissing and saving the dosage
    val onDismissDialog = {
        showDosageDialog = false
    }

    val onSaveDosage = {
        // Handle saving the dosage logic here, e.g., updating the reminder
        showDosageDialog = false
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Reminder name and start date
        TextField(
            value = "Đơn thuốc ngày ${LocalDate.now()}",
            onValueChange = {},
            label = { Text("Tên đơn thuốc") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Row for "Ngày bắt đầu" and "Số ngày uống"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Start date
            TextField(
                value = LocalDate.now().toString(),
                onValueChange = {},
                label = { Text("Ngày bắt đầu") },
                modifier = Modifier.weight(1f)
            )
            // Number of days duration
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Số ngày uống") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reminder list
        LazyColumn {
            items(reminders) { reminder ->
                ReminderRow(reminder = reminder, onDelete = onDelete, onDosageClick = {
                    selectedReminder = reminder
                    showDosageDialog = true
                })
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onAddReminder, modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                Text("Thêm thuốc")
            }
            Button(onClick = onSchedule, modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text("Hẹn giờ")
            }
        }
    }

    // Dosage Dialog (Slide window)
    if (showDosageDialog && selectedReminder != null) {
        DosageDialog(
            reminder = selectedReminder, // Pass the selected reminder for dosage input
            onDismiss = onDismissDialog,
            onSaveDosage = onSaveDosage
        )
    }
}

// Reminder Row with Delete button and Thêm liều lượng button
@Composable
fun ReminderRow(reminder: Product, onDelete: (Product) -> Unit, onDosageClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 5.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row for name and description with delete button on the right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = reminder.name,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = reminder.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                IconButton(onClick = { onDelete(reminder) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thêm liều lượng button at the bottom
            Button(
                onClick = onDosageClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm liều lượng")
            }
        }
    }
}

// Slide window dialog for dosage input
@Composable
fun DosageDialog(
    reminder: Product?,
    onDismiss: () -> Unit,
    onSaveDosage: () -> Unit
) {
    var morning by remember { mutableStateOf("") }
    var noon by remember { mutableStateOf("") }
    var afternoon by remember { mutableStateOf("") }
    var evening by remember { mutableStateOf("") }
    var selectedMealTime by remember { mutableStateOf("Trước ăn") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Apply blur effect to the AddFormScreen content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f) // Lower opacity to simulate blur
                .background(Color.Gray)
        )

        // The actual dialog content
        Column(
            modifier = Modifier
                .fillMaxHeight(0.4f) // Make Dosage Dialog occupy 80% of screen height
                .background(Color.White)
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            // Header row with delete icon and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Delete, contentDescription = "Close")
                }
                Text("Thêm liều lượng", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = onSaveDosage) {
                    Text("Xong")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product name
            Text("Tên sản phẩm: ${reminder?.name ?: ""}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // 2x2 grid for dosage times
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text("Sáng:")
                        BasicTextField(
                            value = morning,
                            onValueChange = { morning = it },
                            modifier = Modifier.fillMaxWidth().padding(5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text("Trưa:")
                        BasicTextField(
                            value = noon,
                            onValueChange = { noon = it },
                            modifier = Modifier.fillMaxWidth().padding(5.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text("Chiều:")
                        BasicTextField(
                            value = afternoon,
                            onValueChange = { afternoon = it },
                            modifier = Modifier.fillMaxWidth().padding(5.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text("Tối:")
                        BasicTextField(
                            value = evening,
                            onValueChange = { evening = it },
                            modifier = Modifier.fillMaxWidth().padding(5.dp)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // "Trước ăn" button
                Button(
                    onClick = { selectedMealTime = "Trước ăn" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMealTime == "Trước ăn") Color.Blue else Color.Gray,
                        contentColor = if (selectedMealTime == "Trước ăn") Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Trước ăn")
                }

                // "Sau ăn" button
                Button(
                    onClick = { selectedMealTime = "Sau ăn" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMealTime == "Sau ăn") Color.Blue else Color.Gray,
                        contentColor = if (selectedMealTime == "Sau ăn") Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sau ăn")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFormPreview() {
    val reminders = listOf(
        Product(name = "Medicine 1", description = "ABC"),
        Product(name = "Medicine 2", description = "XYZ")
    )

    AddFormScreen(
        reminders = reminders,
        onAddReminder = { /* Handle add reminder action */ },
        onSchedule = { /* Handle schedule action */ },
        onDelete = { /* Handle delete action */ }
    )
}
