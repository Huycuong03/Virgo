package com.example.virgo.ui.screen.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SearchToReminderScreen(onAddToReminder: (Product) -> Unit) {
    val products = listOf(
        Product("Thực phẩm bảo vệ sức khỏe G-TEEN", "Hỗ trợ phát triển cho bé gái giai đoạn dậy thì"),
        Product("SIRO G+ Kenko", "Hỗ trợ tiêu hóa và tăng cường sức khỏe"),
        Product("Siro Grow Gold G&P", "Giúp ăn ngon, khắc phục tình trạng biếng ăn"),
        Product("Cốm Trí Não G-Brain", "Bổ sung dưỡng chất tốt cho trí não"),
        Product("Siro Grow Nano G&P", "Giúp trẻ phát triển hệ xương chắc khỏe")
    )

    var searchText by remember { mutableStateOf("") }
    val filteredProducts = if (searchText.isEmpty()) {
        emptyList()
    } else {
        products.filter { it.name.contains(searchText, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { /* Handle back navigation */ },
                modifier = Modifier
                    .padding(end = 8.dp) // Space from screen edge
                    .size(30.dp))
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(20.dp)
                )
            }
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Nhập tên thuốc...") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product list
        if (filteredProducts.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp),
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
                    text = if (searchText.isEmpty()) {
                        "Bắt đầu tìm kiếm sản phẩm"
                    } else {
                        "Không tìm thấy sản phẩm nào phù hợp"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn {
                items(filteredProducts) { product ->
                    ProductRow(product = product, onAddToReminder = onAddToReminder)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))  // This ensures the button stays at the bottom
        Button(
            onClick = { /* Handle continue action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tiếp tục")
        }
    }
}



@Composable
fun ProductRow(product: Product, onAddToReminder: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                product.name,
                style = MaterialTheme.typography.bodyLarge, // Updated style
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                product.description,
                style = MaterialTheme.typography.bodyMedium, // Updated style
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(onClick = { onAddToReminder(product) }) {
            Text("Thêm")
        }
    }
}

data class Product(val name: String, val description: String)

@Preview(showBackground = true)
@Composable
fun SearchRemindPreview(){
    SearchToReminderScreen(onAddToReminder = {})
}