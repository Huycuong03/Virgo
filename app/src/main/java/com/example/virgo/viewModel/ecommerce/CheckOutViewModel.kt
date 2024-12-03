package com.example.virgo.viewModel.ecommerce

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Order
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Payment
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FirebaseFirestore

class CheckOutViewModel: ViewModel() {
    private val ref = FirebaseDatabase
        .getInstance(SharedPreferencesManager.getString("firebase_database")?:"")
        .getReference("cart")
        .child(SharedPreferencesManager.getUID())
    private val db = FirebaseFirestore.getInstance().collection("orders")
    private val _cartItems = mutableStateListOf<ProductWithQuantity>()
    val cartItems: State<List<ProductWithQuantity>> get() = derivedStateOf {
        _cartItems.toList()
    }

    fun loadCartItems(idList: List<String>) {
        ref.get().addOnSuccessListener { snapshot ->
                _cartItems.addAll(
                    snapshot.children.mapNotNull { it.getValue<ProductWithQuantity>()?.copy(id = it.key) }
                        .filter { idList.contains(it.id) }
                )
            }
    }

    fun checkout(total: Float, callback: () -> Unit) {
        db.add(
            Order(
                products = _cartItems,
                status = "to-pay",
                payment = Payment(
                    method = "COD",
                    total = total
                )
            )
        ).addOnSuccessListener {
            for (cartItem in _cartItems) {
                ref.child(cartItem.id?:"").setValue(null)
            }
            callback()
        }
    }
}