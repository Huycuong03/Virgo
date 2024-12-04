package com.example.virgo.ui.screen.reminder

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.route.reminder.ReminderListRoute
import com.example.virgo.route.reminder.ReminderTimeRoute
import com.example.virgo.ui.screen.lib.TopBar
import com.example.virgo.viewModel.reminder.AddReminderViewModel
import com.google.firebase.Timestamp
import java.time.LocalDate

@Composable
fun AddFormScreen(
    productIDs: List<String>,
    navController: NavController
) {
    val viewModel: AddReminderViewModel = viewModel()
    val name = viewModel.name.value
    val duration = viewModel.duration.value
    val skip = viewModel.skip.value
    val reminders = viewModel.products.value

    LaunchedEffect(key1 = productIDs) {
        viewModel.fetchProducts(productIDs)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            leadingIcon = { 
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }, 
            title = { Text(text = "Phiếu tạo nhắc nhở") }
        )
        TextField(
            value = name,
            onValueChange = {viewModel.onChangeName(it)},
            label = { Text("Tên đơn thuốc") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = Timestamp.now().toDate().toString(),
            onValueChange = {},
            label = { Text("Ngày bắt đầu") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = if (duration == 0) "" else duration.toString(),
                onValueChange = {
                    // Safely handle non-digit inputs
                    val newValue = it.toIntOrNull() // Converts to Int or returns null if input is invalid
                    if (newValue != null) {
                        viewModel.onChangeDuration(newValue) // Update with valid integer
                    } else {
                        viewModel.onChangeDuration(0) // Default to 0 for invalid input
                    }
                },
                label = { Text("Số ngày uống") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = if (skip == 0) "" else skip.toString(),
                onValueChange = {
                    // Safely handle non-digit inputs
                    val newValue = it.toIntOrNull() // Converts to Int or returns null if input is invalid
                    if (newValue != null) {
                        viewModel.onChangeSkip(newValue) // Update with valid integer
                    } else {
                        viewModel.onChangeSkip(0) // Default to 0 for invalid input
                    }
                },
                label = { Text("Số ngày nghỉ") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reminder list
        LazyColumn {
            items(reminders) { reminder ->
                ReminderRow(
                    reminder = reminder,
                    onDelete = { viewModel.deleteProduct(reminder) }
                ) {
                    viewModel.onChangeQuantity(it)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(ReminderTimeRoute(reminder = viewModel.getReminder()))
                }
            ) {
                Text("Tiếp tục", fontSize = 12.sp)
            }
        }
    }

}


@Composable
fun ReminderRow(reminder: ProductWithQuantity, onDelete: (ProductWithQuantity) -> Unit, onUpdate: (ProductWithQuantity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 5.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    reminder.product?.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    reminder.product?.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(onClick = { onDelete(reminder) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if ((reminder.quantity ?: 0) != 0) {
                        onUpdate(reminder.copy(quantity = reminder.quantity ?.let { it - 1 }))
                    }
                }) {
                    Text("-", fontSize = 30.sp)
                }
                Text(text = "${reminder.quantity}")
                IconButton(onClick = {
                    onUpdate(reminder.copy(quantity = reminder.quantity ?.let { it + 1 }))
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                }
            }
        }
    }
}

@Composable
fun ScheduleDialog(
    onDismiss: () -> Unit,
    onSaveSchedule: (List<Int>) -> Unit
) {
    var morningSelected by remember { mutableStateOf(0) }
    var noonSelected by remember { mutableStateOf(0) }
    var afternoonSelected by remember { mutableStateOf(0) }
    var eveningSelected by remember { mutableStateOf(0) }
    var selectedMealTime by remember { mutableStateOf(0) }

    fun check(x: Int): Boolean{
        return if (x==1) true else false
    }
    fun transfer(x: Boolean): Int{
        return if(x) 1 else 0
    }

    fun transferNote(x: String): Int{
        return if (x=="Trước ăn") 0 else 1
    }

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
                Text("Hẹn giờ nhắc", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = { onSaveSchedule(arrayListOf(morningSelected, noonSelected, afternoonSelected, eveningSelected, selectedMealTime)) }) {
                    Text("Xong")
                }
            }

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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Sáng:")
                        Checkbox(
                            checked = check(morningSelected),
                            onCheckedChange = { morningSelected = transfer(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Trưa:")
                        Checkbox(
                            checked = check(noonSelected),
                            onCheckedChange = { noonSelected = transfer(it) },
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Chiều:")
                        Checkbox(
                            checked = check(afternoonSelected),
                            onCheckedChange = { afternoonSelected = transfer(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tối:")
                        Checkbox(
                            checked = check(eveningSelected),
                            onCheckedChange = { eveningSelected = transfer(it) },
                            modifier = Modifier.padding(start = 15.dp)
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
                    onClick = { selectedMealTime = 0},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMealTime == transferNote("Trước ăn")) Color.Blue else Color.Gray,
                        contentColor = if (selectedMealTime == transferNote("Trước ăn")) Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Trước ăn")
                }

                // "Sau ăn" button
                Button(
                    onClick = { selectedMealTime = 1},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedMealTime == transferNote("Sau ăn")) Color.Blue else Color.Gray,
                        contentColor = if (selectedMealTime == transferNote("Sau ăn")) Color.White else Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sau ăn")
                }
            }
        }
    }
}
