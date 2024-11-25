package com.example.virgo.ui.screen.appointment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.virgo.model.appointment.Department
import com.example.virgo.ui.screen.lib.Gallery
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.theme.ColorBackground

@Composable
fun DepartmentSelection(onSelection: (Department) -> Unit) {
    val departmentList = Department.entries.toList()
    var searchText by remember { mutableStateOf("") }
    val departmentResultList = remember {
        mutableStateListOf(*departmentList.toTypedArray())
    }

    Column(
        modifier = Modifier
            .background(ColorBackground)
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchText,
            placeholder = "Search for department",
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        ) {
            searchText = it
            departmentResultList.clear()
            for (department in departmentList) {
                if (department.title.contains(searchText, ignoreCase = true)) {
                    departmentResultList.add(department)
                }
            }
        }

        Gallery(items = departmentResultList, columns = 3) {
            val department = it as Department
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .clickable { onSelection(department) }
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(150.dp)
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    Image(
                        imageVector = department.icon,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = department.title)
                }
            }
        }
    }
}