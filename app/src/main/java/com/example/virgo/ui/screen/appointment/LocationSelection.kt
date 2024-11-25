package com.example.virgo.ui.screen.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.R
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.theme.ColorBackground

@Composable
fun LocationSelection(
    currentLocation: String,
    onSelection: (String) -> Unit
) {
    val locationList = stringArrayResource(id = R.array.provinces).toList()
    var searchText by remember { mutableStateOf("") }
    val locationResultList = remember {
        mutableStateListOf(*locationList.toTypedArray())
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
            placeholder = "Search for location",
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        ) {
            searchText = it
            locationResultList.clear()
            for (location in locationList) {
                if (location.contains(searchText, ignoreCase = true)) {
                    locationResultList.add(location)
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                items(locationResultList) { location ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelection(location) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = location,
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp
                        )
                        if (location == currentLocation) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = Color(0xFF007BFF)
                            )
                        }
                    }
                }
            }
        }
    }
}