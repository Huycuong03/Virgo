package com.example.virgo.ui.screen.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.R
import com.example.virgo.model.lib.Address
import com.example.virgo.route.ecommerce.CartRoute
import com.example.virgo.route.profile.ChangeAddressRoute
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.viewModel.ecommerce.CheckOutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAddressScreen(navController: NavController) {
    val viewModel:CheckOutViewModel= viewModel()
    val addresses = viewModel.address.value
    LaunchedEffect(Unit) {
        viewModel.loadAddress()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Quản lý địa chỉ", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.navigate(ChangeAddressRoute(-1)) },
                    ) {
                        Text(text = "Thêm địa chỉ")
                    }
                }
            }
        }
    ) { innerPadding ->
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize() // Allow the content to take the full available space
        ) {
            AddressBox(
                addresses = addresses
            ) {
                navController.navigate(ChangeAddressRoute(it))
            }
        }
    }
}

@Composable
fun AddressBox(addresses: List<Address>, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .background(ColorBackground)
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                itemsIndexed(addresses) { index, address ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = address.name.toString(),
                                    fontWeight = FontWeight.Bold
                                )
                                TextButton(onClick = {
                                    onClick(index)
                                }) {
                                    Text(
                                        text = "Sửa",
                                        style = MaterialTheme.typography.bodyMedium.copy()
                                    )
                                }
                            }
                            Text(
                                text = "Số điện thoại: " + address.phoneNumber.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                            Text(
                                text = address.toString(),
                                style = MaterialTheme.typography.bodySmall.copy(),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}