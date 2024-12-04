package com.example.virgo.ui.screen.reminder

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.route.reminder.AddFormRoute
import com.example.virgo.ui.screen.lib.NavIcon
import com.example.virgo.ui.screen.lib.TopBar
import com.example.virgo.viewModel.SearchViewModel

@Composable
fun SearchToReminderScreen(navController: NavController) {
    val viewModel: SearchViewModel = viewModel()
    val searchText = viewModel.searchText.value
    val productResultList = viewModel.productResultList.value
    val addedProductID = viewModel.addedProductID.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar with search functionality
        TopBar(
            leadingIcon = {
                NavIcon {
                    navController.popBackStack()
                }
            },
            title = {
                com.example.virgo.ui.screen.lib.SearchBar(query = searchText,
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onChangeSearchText("") }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                    }) {
                    viewModel.onChangeSearchText(it)
                }
            },
            actions = {}
        )

        // Search Results
        if (searchText.isNotEmpty()) {
            if (productResultList.isEmpty()) {
                // No results found
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "No results",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Không tìm thấy sản phẩm nào phù hợp",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(productResultList) { product ->
                            ProductRow(
                                product = product,
                                onAddToReminder = { addedProduct ->
                                    if (!addedProductID.contains(addedProduct.id)) {
                                        viewModel.addProduct(addedProduct)
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Optional spacing above the button

                    Button(
                        onClick = {

                            Log.d("Size", addedProductID.size.toString())
                            navController.navigate(AddFormRoute(addedProductID))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tiếp Tục")
                    }
                }

            }
        } else {
            // Prompt to start searching
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Start searching",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nhập từ khóa để tìm kiếm sản phẩm",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@Composable
fun ProductRow(product: Product, onAddToReminder: (Product) -> Unit) {
    val isAdded = remember { mutableStateOf(false) } // Track if the product is added

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            product.name?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            product.description?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Button(
            onClick = {
                if (!isAdded.value) {
                    onAddToReminder(product) // Add product to reminder list
                    isAdded.value = true // Disable button after clicking
                }
            },
            enabled = !isAdded.value // Disable button if already added
        ) {
            Text(if (isAdded.value) "Đã thêm" else "Thêm")
        }
    }
}

