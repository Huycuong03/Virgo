package com.example.virgo.viewModel.reminder

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Reminder
import com.example.virgo.sqlite.ReminderDatabaseHelper
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class AddReminderViewModel() : ViewModel() {
    private val _name = mutableStateOf("Đơn thuốc ngày ${LocalDate.now()}")
    val name: State<String> get() = _name

    private val _duration = mutableIntStateOf(0)
    val duration: State<Int> get() = _duration

    private val _skip = mutableIntStateOf(0)
    val skip: State<Int> get() = _skip

    private val _alarms = mutableStateListOf<String>()
    val alarms: State<List<String>> get() = derivedStateOf {
        _alarms.toList()
    }
    private val _products = mutableStateListOf<ProductWithQuantity>()
    val products: State<List<ProductWithQuantity>> get() = derivedStateOf {
        _products.toList()
    }


    fun fetchProducts(ids: List<String>){
        FirebaseFirestore.getInstance().collection("products").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val product = doc.toObject<Product>().copy(id = doc.id)
                if(product.id in ids){
                    val productWithQuantity = ProductWithQuantity(product, 1, false)
                    _products.add(productWithQuantity)
                }
            }
        }
    }
    fun deleteProduct(product: ProductWithQuantity){
        _products.remove(product)
    }

    fun onChangeName(name: String){
        _name.value = name
    }
    fun onChangeDuration(duration: Int) {
        _duration.intValue = duration
    }

    fun onChangeSkip(skip: Int) {
        _skip.intValue = skip
    }

    fun onChangeQuantity(product: ProductWithQuantity){
        val index = _products.indexOfFirst { it.product?.id == product.product?.id }
        _products[index] = product
    }

    fun getReminder(): Reminder {
        return Reminder(
            name = _name.value,
            duration = _duration.intValue,
            skip = _skip.intValue,
            products = _products.toList()
        )
    }
}
