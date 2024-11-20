package com.example.virgo.ui.screen.appointment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSection() {
    val provinces = stringArrayResource(id = R.array.provinces)
    var selectedProvince by remember { mutableStateOf("Tất cả") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showProvinceSheet by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clickable { showProvinceSheet = true }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = selectedProvince,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = { /* TODO: Implement cancel action */ }) {
            Text(
                text = "Hủy",
                color = Color(0xFF007BFF),
                fontSize = 20.sp
            )
        }
    }

    if (showProvinceSheet) {
        ModalBottomSheet(
            onDismissRequest = { showProvinceSheet = false },
            sheetState = sheetState
        ) {
            ProvinceSelectionContent(
                provinces = provinces,
                currentSelection = selectedProvince,
                onSelectionDone = { selected ->
                    selectedProvince = selected
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        showProvinceSheet = false
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvinceSelectionContent(
    provinces: Array<String>,
    currentSelection: String,
    onSelectionDone: (String) -> Unit
) {
    var selectedProvince by remember { mutableStateOf(currentSelection) }
    var searchProvinceText by remember { mutableStateOf("") }
    val filteredProvinces = provinces.filter { it.contains(searchProvinceText, ignoreCase = true) }

    Column(
        modifier = Modifier
            .background(Color(0xFFF1F5FB))
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = CircleShape),
            verticalAlignment = Alignment.CenterVertically
        ){
            TextField(
                value = searchProvinceText,
                onValueChange = { searchProvinceText = it },
                placeholder = { Text("Tìm tỉnh/thành phố") },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.Gray
                )
            )
        }

        // List of provinces
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                items(filteredProvinces) { province ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedProvince = province }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = province,
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp
                        )
                        if (province == selectedProvince) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Đã chọn",
                                tint = Color(0xFF007BFF)
                            )
                        }
                    }
                }
            }
        }

        // Done button
        Button(
            onClick = { onSelectionDone(selectedProvince) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
        ) {
            Text(
                text = "Xong",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun MedicalSearchScreen() {
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
        modifier = Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
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
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Icon(Icons.Filled.Search, contentDescription = "Search Icon")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

                OutlinedTextField(
                    value = categorySearchText,
                    onValueChange = { categorySearchText = it },
                    placeholder = { Text("Tìm kiếm danh mục") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Gray
                    )
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
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
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
            .height(100.dp) // Set fixed height for the Card
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp), // Set the radius of the corners
        colors = CardDefaults.cardColors(containerColor = Color.White), // Ensure background is always white
        elevation = CardDefaults.cardElevation(4.dp) // Optional: Add elevation for shadow
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize() // Ensure Row takes up full space of the Card
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_holder),
                contentDescription = "Medical Center Icon",
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("Đa khoa", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicalSearchScreen() {
    MedicalSearchScreen()
}//