package com.example.virgo.viewModel.reminder

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.sqlite.ReminderDatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddReminderViewModel() : ViewModel() {
    private lateinit var dbHelper: ReminderDatabaseHelper

    private val _name = mutableStateOf("Đơn thuốc ngày ${LocalDate.now()}")
    val name: State<String> get() = _name

    private val _dateCreated = mutableStateOf(LocalDate.now().toString())
    val dateCreated: State<String> get() = _dateCreated

    private val _duration = mutableStateOf(0)
    val duration: State<Int> get() = _duration

    private val _skip = mutableStateOf(0)
    val skip: State<Int> get() = _skip

    private val _note = mutableStateOf("Trước ăn")
    val note: State<String> get() = _note

    private val _isActive = mutableStateOf(false)
    val isActive: State<Boolean> get() = _isActive

    private val _alarms = mutableStateListOf<String>()
    val alarms: State<List<String>> get() = derivedStateOf {
        _alarms.toList()
    }

    private val _products = mutableStateListOf<ProductWithQuantity>()
    val products: State<List<ProductWithQuantity>> get() = derivedStateOf {
        _products.toList()
    }

    fun init(context: Context) {
        dbHelper = ReminderDatabaseHelper(context)
    }

    fun addReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            dbHelper.createReminder(_name.value, _dateCreated.value, _duration.value, _skip.value, _note.value, _isActive.value, _alarms, _products)
        }
    }

    fun onChangeName(name: String){
        _name.value = name
    }
    fun onChangeDuration(duration: Int) {
        _duration.value = duration
    }

    fun onChangeSkip(skip: Int) {
        _skip.value = skip
    }

    fun onChangeIsActive(isActive: Boolean) {
        _isActive.value = isActive
    }

    fun onChangeQuantity(product: ProductWithQuantity){
        val index = _products.indexOfFirst { it.product?.id == product.product?.id }
        _products[index] = product
    }

    fun onChangeAlarm(alarms: List<Int>){
        if(alarms[0] == 1){
            _alarms.add("07:30")
        }
        if(alarms[1] == 1){
            _alarms.add("11:30")
        }
        if(alarms[2] == 1){
            _alarms.add("16:00")
        }
        if(alarms[3] == 1){
            _alarms.add("20:00")
        }
        if(alarms[4] == 1){
            _note.value = "Sau ăn"
        }
    }
}
