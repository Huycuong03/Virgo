package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.appointment.Department
import com.example.virgo.model.appointment.Facility
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SearchFacilityViewModel : ViewModel() {
    private val _searchText = mutableStateOf("")
    private val _selectedLocation = mutableStateOf("All locations")
    private val _selectedDepartment = mutableStateOf(Department.ALL_DEPARTMENTS)
    private var _facilityList = mutableListOf<Facility>()
    private var _facilityResultList = mutableStateListOf<Facility>()

    val searchText: State<String> get() = _searchText
    val selectedLocation: State<String> get() = _selectedLocation
    val selectedDepartment: State<Department> get() = _selectedDepartment
    val facilityResultList: State<List<Facility>> get() = derivedStateOf {
        _facilityResultList.toList()
    }

    init {
        FirebaseFirestore.getInstance().collection("facilities").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val facility = doc.toObject<Facility>()
                _facilityList.add(facility)
                _facilityResultList.add(facility)
            }
        }
    }

    fun onSelectLocation(location: String) {
        _selectedLocation.value = location
        onChange()
    }

    fun onSelectDepartment(department: Department) {
        _selectedDepartment.value = department
        onChange()
    }

    fun onChangeSearchText(keyword: String) {
        _searchText.value = keyword
        onChange()
    }

    private fun onChange() {
        _facilityResultList.clear()
        for (facility in _facilityList) {
            if ( facility.name?.contains(_searchText.value) == true ) {
                if (_selectedLocation.value != "All locations" && facility.address?.city != _selectedLocation.value) {
                    continue
                }
                if (_selectedDepartment.value != Department.ALL_DEPARTMENTS && !facility.departments.contains(_selectedDepartment.value.id)) {
                    continue
                }
                _facilityResultList.add(facility)
            }
        }
    }
}