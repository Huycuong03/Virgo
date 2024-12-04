package com.example.virgo.viewModel.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Order
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class OrderTrackingViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance().collection("orders")
    private val _selectedStatus = mutableStateOf(OrderStatus.PROCESSING)
    private val _orderListByStatus = mutableStateListOf<Order>()

    val selectedStatus: State<OrderStatus> get() = _selectedStatus
    val orderListByStatus: State<List<Order>> get() = derivedStateOf {
        _orderListByStatus.toList()
    }

    fun onSelectStatus(status: OrderStatus) {
        _selectedStatus.value = status
        _orderListByStatus.clear()
        db.whereEqualTo("uid", SharedPreferencesManager.getUID()).whereEqualTo("status", status.text).get().addOnSuccessListener { documents ->
            _orderListByStatus.addAll(documents.map { it.toObject<Order>().copy(id = it.id) })
        }
    }

    fun updateStatus(order: Order) {
        db.document(order.id?:"").update("status", order.status).addOnSuccessListener {
            onSelectStatus(_selectedStatus.value)
        }
    }
}