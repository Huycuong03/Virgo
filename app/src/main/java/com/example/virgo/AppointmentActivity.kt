
package com.example.btl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.Greeting
import com.example.virgo.R
import com.example.virgo.ui.theme.VirgoTheme

class AppointmentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VirgoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBarSection() {
    val provinces = stringArrayResource(id = R.array.provinces)

    var expanded by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf(provinces[0]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true } // Mở menu khi nhấn vào
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedProvince,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Mở danh sách tỉnh thành"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            provinces.forEach { province ->
                DropdownMenuItem(
                    text = {Text(province)},
                    onClick = {
                        selectedProvince = province
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { /* TODO: Implement cancel action */ }) {
            Text("Hủy", color = Color(0xFF007BFF))
        }
    }
}

@Composable
fun MedicalSearchScreen(modifier: Modifier = Modifier) {
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val categories = stringArrayResource(id = R.array.medical_departments).mapIndexed { index, category ->
        category to when (index % 5) { // Placeholder icon mapping (use actual icon resources here)
            0 -> R.drawable.image_holder
            1 -> R.drawable.image_holder
            2 -> R.drawable.image_holder
            3 -> R.drawable.image_holder
            else -> R.drawable.image_holder
        }
    }
    val filteredCategories = if (searchText.isNotEmpty()) {
        categories.filter { it.first.contains(searchText, ignoreCase = true) }
    } else {
        categories
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5FB))
            .padding(16.dp)
    ) {
        TopAppBarSection()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            searchText = searchText,
            isSearching = isSearching,
            onSearchBarClicked = {
                isSearching = true
                searchText = ""
            },
            onSearchIconClicked = {isSearching = true },
            onCloseClicked = { isSearching = false; searchText = "" },
            onTextChange = { searchText = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isSearching) {
            CategoryGrid(
                categories = filteredCategories,
                onCategoryClick = {
                    searchText = it
                    isSearching = false
                }
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            ResultCount()
            Spacer(modifier = Modifier.height(16.dp))
            MedicalList()
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    isSearching: Boolean,
    onSearchBarClicked: () -> Unit,
    onSearchIconClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = CircleShape)
            .padding(8.dp)
            .clickable { onSearchBarClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchText,
            onValueChange = {onTextChange(it)},
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f) ,
            placeholder = { Text("Nhập tên bệnh viện") },
        )
        IconButton(onClick = { onSearchIconClicked() }) {
            Icon(Icons.Filled.Search, contentDescription = "Search Icon")
        }
        if (isSearching) {
            IconButton(onClick = { onCloseClicked() }) {
                Icon(Icons.Filled.Close, contentDescription = "Close Icon")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryGrid(categories: List<Pair<String, Int>>, onCategoryClick: (String) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { category ->
            CategoryItem(
                name = category.first,
                iconRes = category.second,
                onClick = { onCategoryClick(category.first) }
            )
        }
    }
}

@Composable
fun CategoryItem(name: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5FB)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}



@Composable
fun ResultCount() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "10 Kết Quả",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun MedicalList() {
    val items = listOf(
        "Trung tâm Xét nghiệm Y khoa Medilab Sài Gòn",
        "Phòng khám Đa khoa Meccare",
        "Phòng khám Đa khoa trực tuyến BS. Nguyễn Thanh Tâm",
        "Trung tâm Tiêm chủng VNVC Quận 1",
        "Trung tâm Chăm sóc sức khỏe European Wellness Việt Nam (EWH)"
    )

    LazyColumn {
        items(items) { item ->
            MedicalItem(name = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MedicalItem(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Medical Center Icon",
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("Đa khoa", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicalSearchScreen() {
    MedicalSearchScreen()
}
