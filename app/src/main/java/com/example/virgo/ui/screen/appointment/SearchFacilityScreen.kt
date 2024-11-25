package com.example.virgo.ui.screen.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ModalBottomSheet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.route.appointment.FacilityDetailRoute
import com.example.virgo.ui.screen.lib.RowItem
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.viewModel.SearchFacilityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFacilityScreen(navController: NavController) {
    val viewModel: SearchFacilityViewModel = viewModel()
    val searchText = viewModel.searchText.value
    val selectedLocation = viewModel.selectedLocation.value
    var locationSheetState by remember {
        mutableStateOf(false)
    }
    val selectedDepartment = viewModel.selectedDepartment.value
    var departmentSheetState by remember { mutableStateOf(false) }
    val facilityResultList = viewModel.facilityResultList

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(16.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                query = searchText,
                placeholder = "Search for medical facilities",
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onChangeSearchText("") }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            ) {
                viewModel.onChangeSearchText(it)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { locationSheetState = true }) {
                    Text(text = selectedLocation)
                }
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
            
            Spacer(modifier = Modifier.width(7.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { departmentSheetState = true }) {
                    Text(text = selectedDepartment.title)
                }
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        }

        if (locationSheetState) {
            ModalBottomSheet(onDismissRequest = { locationSheetState = false }) {
                LocationSelection (currentLocation = selectedLocation) {
                    viewModel.onSelectLocation(it)
                    locationSheetState = false
                }
            }
        }

        if (departmentSheetState) {
            ModalBottomSheet(onDismissRequest = { departmentSheetState = false }) {
                DepartmentSelection() {
                    viewModel.onSelectDepartment(it)
                    departmentSheetState = false
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row (
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${facilityResultList.value.size} results")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ) {
            items(facilityResultList.value) {facility ->
                RowItem(name = facility.name.toString(), description = facility.description, image = facility.avatarImage) {
                    facility.id?.let {
                        navController.navigate(FacilityDetailRoute(facility.id))
                    }
                }
            }
        }
    }
}