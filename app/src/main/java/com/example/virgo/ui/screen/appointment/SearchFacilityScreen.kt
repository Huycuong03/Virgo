package com.example.virgo.ui.screen.appointment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
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
import com.example.virgo.R


@Composable
fun TopAppBarSection() {
    val provinces = stringArrayResource(id = R.array.provinces)
    var expanded by remember { mutableStateOf(false) }
    var searchProvinceText by remember { mutableStateOf("") }
    val filteredProvinces = provinces.filter { it.contains(searchProvinceText, ignoreCase = true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchProvinceText.isNotEmpty()) searchProvinceText else "Chọn tỉnh thành",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Mở danh sách tỉnh thành"
                )
            }




            Spacer(modifier = Modifier.weight(1f))




            TextButton(onClick = { /* TODO: Implement cancel action */ }) {
                Text("Hủy", color = Color(0xFF007BFF))
            }
        }




        if (expanded) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = searchProvinceText,
                    onValueChange = { searchProvinceText = it },
                    placeholder = { Text("Tìm kiếm tỉnh thành") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )




                LazyColumn {
                    items(filteredProvinces) { province ->
                        DropdownMenuItem(
                            text = { Text(province) },
                            onClick = {
                                searchProvinceText = province
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun SearchFacilityScreen(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Chọn danh mục") }
    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = stringArrayResource(id = R.array.medical_departments)


    val medicalCenters = listOf(
        "Trung tâm Xét nghiệm Y khoa Medilab Sài Gòn",
        "Phòng khám Đa khoa Meccare",
        "Phòng khám Đa khoa trực tuyến BS. Nguyễn Thanh Tâm",
        "Trung tâm Tiêm chủng VNVC Quận 1",
        "Trung tâm Chăm sóc sức khỏe European Wellness Việt Nam (EWH)",
        "Bệnh viện Đa khoa Quốc tế Vinmec",
        "Bệnh viện Đại học Y Dược TP.HCM",
        "Bệnh viện Nhi Đồng 2",
        "Phòng khám Đa khoa Medic Bình Dương",
        "Trung tâm Kiểm soát Bệnh tật TP.HCM (HCDC)"
    )




    val filteredMedicalCenters = medicalCenters.filter {
        it.contains(searchText, ignoreCase = true)
    }




    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5FB))
            .padding(16.dp)
    ) {
        TopAppBarSection()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(searchText = searchText, onTextChange = { searchText = it })
        Spacer(modifier = Modifier.height(16.dp))
        FilterBar(
            selectedCategory = selectedCategory,
            categories = categories,
            expanded = categoryExpanded,
            onExpand = { categoryExpanded = !categoryExpanded },
            onCategorySelected = {
                selectedCategory = it
                categoryExpanded = false
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ResultCount(filteredMedicalCenters.size)
        Spacer(modifier = Modifier.height(16.dp))
        MedicalList(filteredMedicalCenters)
    }
}




@Composable
fun SearchBar(searchText: String, onTextChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = CircleShape)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchText,
            onValueChange = { onTextChange(it) },
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f),
            placeholder = { Text("Nhập từ khóa tìm kiếm") },
        )
        Icon(Icons.Filled.Search, contentDescription = "Search Icon")
    }
}




@Composable
fun FilterBar(
    selectedCategory: String,
    categories: Array<String>,
    expanded: Boolean,
    onExpand: () -> Unit,
    onCategorySelected: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onExpand() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCategory,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Mở danh sách danh mục"
            )
        }




        if (expanded) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                var categorySearchText by remember { mutableStateOf("") }
                val filteredCategories = categories.filter { it.contains(categorySearchText, ignoreCase = true) }




                TextField(
                    value = categorySearchText,
                    onValueChange = { categorySearchText = it },
                    placeholder = { Text("Tìm kiếm danh mục") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )




                LazyColumn {
                    items(filteredCategories) { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = { onCategorySelected(category) }
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun ResultCount(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$count Kết Quả",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}




@Composable
fun MedicalList(items: List<String>) {
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
    SearchFacilityScreen()
}