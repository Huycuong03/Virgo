package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.viewModel.profileTest.ChangeAddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAddressScreen(navController: NavController, addressIndex: Int){
    val labels = listOf("Họ và tên", "Số điện thoại", "Thành phố", "Quận", "Phường","Đường", "Số nhà")
    val viewModel: ChangeAddressViewModel = viewModel()
    val address = viewModel.address.value

    LaunchedEffect(key1 = addressIndex) {
        if (addressIndex >= 0) {
            viewModel.loadAddress(addressIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Địa chỉ")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onDelete {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Delete, null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.onSave {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cập nhật")
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(labels.size) { index ->
                    // Determine the label and corresponding state for the input field
                    val label = labels[index]
                    val text = when (index) {
                        0 -> address.name
                        1 -> address.phoneNumber
                        2 -> address.city
                        3 -> address.district
                        4 -> address.ward
                        5 -> address.street
                        6 -> address.houseNumber
                        else -> address.phoneNumber
                    }

                    TextField(
                        value = text?:"",
                        onValueChange = { viewModel.onFieldChange(index, it) },
                        label = { Text(label) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        keyboardActions = KeyboardActions.Default
                    )
                }
                item {
                    OutlinedTextField(
                        value = address.note?:"",
                        onValueChange = { viewModel.onFieldChange(labels.size, it) },
                        label = { Text("Note") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        maxLines = 6
                    )
                }
            }
        }
    )
}