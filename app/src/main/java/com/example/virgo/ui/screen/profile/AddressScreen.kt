package com.example.virgo.ui.screen.profile


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.lib.Address
import com.example.virgo.viewModel.profile.AddressViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressForm(navController: NavController) {
    // Create a list of labels for the input fields, including "Họ và tên" at the top
    val labels = listOf("Họ và tên", "Số điện thoại", "Thành phố", "Quận", "Phường","Đường", "Số nhà")
    val viewModel: AddressViewModel = viewModel()
    val fullName = viewModel.fullName.value
    val phoneNumber = viewModel.phoneNumber.value
    val city = viewModel.city.value
    val district = viewModel.district.value
    val ward = viewModel.ward.value
    val street = viewModel.street.value
    val houseNumber = viewModel.houseNumber.value
    val note = viewModel.note.value

    // LazyColumn for the form
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
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.addNewAddress()
                        navController.popBackStack()
                  },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hoàn tất")
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
                    val textState = when (index) {
                        0 -> fullName
                        1 -> phoneNumber
                        2 -> city
                        3 -> district
                        4 -> ward
                        5 -> street
                        6 -> houseNumber
                        else -> phoneNumber
                    }

                    // TextField for each input
                    InputField(label = label, onSave={
                        viewModel.onChangeLabel(label, it)
                    })
                }
                item {
                    OutlinedTextField(
                        value = note,
                        onValueChange = { viewModel.onChangeNote(it) },
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

@Composable
fun InputField(label: String, onSave:(String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = text,
            onValueChange = {
                text =it
                onSave(it) },
            label = {Text(label)},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions.Default
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    AddressForm(navController = NavController(LocalContext.current))
}
