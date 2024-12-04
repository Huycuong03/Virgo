package com.example.virgo.viewModel.ecommerce

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.ecommerce.Order
import com.example.virgo.model.ecommerce.OrderStatus
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.model.lib.Address
import com.example.virgo.model.lib.Payment
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class CheckOutViewModel: ViewModel() {
    private val ref = FirebaseDatabase
        .getInstance(SharedPreferencesManager.getString("firebase_database")?:"")
        .getReference("cart")
        .child(SharedPreferencesManager.getUID())
    private val db = FirebaseFirestore.getInstance()
    private val _cartItems = mutableStateListOf<ProductWithQuantity>()
    private val _addresses = mutableStateListOf<Address>()
    private val _selectedAddress = mutableStateOf<Address?>(null)

    val address: State<List<Address>> get() = derivedStateOf {
        _addresses.toList()
    }
    val cartItems: State<List<ProductWithQuantity>> get() = derivedStateOf {
        _cartItems.toList()
    }
    val selectedAddress: State<Address?> get() = _selectedAddress

    init{
        db.collection("users").document(SharedPreferencesManager.getUID()).get().addOnSuccessListener { snapshot ->
            snapshot.toObject<User>()?.let { user ->
                _addresses.clear()
                _addresses.addAll(user.addresses)
                _selectedAddress.value = _addresses.find { it.default?:false }
            }
        }
    }
    fun load(idList: List<String>) {
        ref.get().addOnSuccessListener { snapshot ->
                _cartItems.clear()
                _cartItems.addAll(
                    snapshot.children.mapNotNull { it.getValue<ProductWithQuantity>()?.copy(id = it.key) }
                        .filter { idList.contains(it.id) }
                )
            }
        db.collection("users").document(SharedPreferencesManager.getUID()).get().addOnSuccessListener { snapshot ->
            snapshot.toObject<User>()?.let { user ->
                _addresses.clear()
                _addresses.addAll(user.addresses)
                _selectedAddress.value = _addresses.find { it.default?:false }
            }
        }
    }

    fun loadAddress() {
        db.collection("users").document(SharedPreferencesManager.getUID()).get().addOnSuccessListener { snapshot ->
            snapshot.toObject<User>()?.let { user ->
                _addresses.clear()
                _addresses.addAll(user.addresses)
                _selectedAddress.value = _addresses.find { it.default?:false }
            }
        }
    }

    fun checkout(total: Float, callback: () -> Unit) {
        db.collection("orders").add(
            Order(
                timestamp = Timestamp.now(),
                products = _cartItems,
                status = OrderStatus.PROCESSING.text,
                payment = Payment(
                    method = "COD",
                    total = total
                ),
                uid = SharedPreferencesManager.getUID()
            )
        ).addOnSuccessListener {
            for (cartItem in _cartItems) {
                ref.child(cartItem.id?:"").setValue(null)
            }
            callback()
        }
    }

    fun onSelectAddress(address: Address) {
        _selectedAddress.value = address
    }
}