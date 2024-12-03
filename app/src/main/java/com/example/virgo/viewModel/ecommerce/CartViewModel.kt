package com.example.virgo.viewModel.ecommerce

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.virgo.model.User
import com.example.virgo.model.ecommerce.Packaging
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject

class CartViewModel : ViewModel() {
    private val ref = FirebaseDatabase.getInstance(SharedPreferencesManager.getString("firebase_database")?:"").getReference("cart").child(SharedPreferencesManager.getUID())
    private val _productsWithQuantities = mutableStateListOf<ProductWithQuantity>()
    private val _totalSum = mutableDoubleStateOf(0.0)
    private val _selectAllChecked = mutableStateOf(false)

    val productsWithQuantities: State<List<ProductWithQuantity>> get() = derivedStateOf {
        _productsWithQuantities.toList()
    }
    val totalSum: State<Double> get() = _totalSum
    val selectAllChecked: State<Boolean> get() = _selectAllChecked

    init {
        init()
    }

    private fun init() {
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                _productsWithQuantities.clear()
                _productsWithQuantities.addAll(snapshot.children.mapNotNull { it.getValue<ProductWithQuantity>()?.copy(id = it.key) })
                calculateTotalSum()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun calculateTotalSum() {
        _totalSum.doubleValue = _productsWithQuantities.filter { it.selected == true }.sumOf {
            val price = it.product?.price ?: 0f
            price.toDouble() * (it.quantity ?: 1)
        }
    }

    fun toggleSelectAll(isChecked: Boolean) {
        _selectAllChecked.value = isChecked
        val tmp: List<ProductWithQuantity> =
            _productsWithQuantities.map { it.copy(selected = isChecked) }
        _productsWithQuantities.clear()
        _productsWithQuantities.addAll(tmp)
        calculateTotalSum()
    }

    fun toggleSelectOne(productWithQuantity: ProductWithQuantity) {
        val tmp: List<ProductWithQuantity> = _productsWithQuantities.map {
            if (it == productWithQuantity && it.selected != null) {
                it.copy(selected = !it.selected)
            } else {
                it
            }
        }
        _productsWithQuantities.clear()
        _productsWithQuantities.addAll(tmp)
        calculateTotalSum()
    }

    fun updateQuantity(cartItem: ProductWithQuantity, increase: Boolean = true) {
        ref.child(cartItem.id?:"").updateChildren(
            hashMapOf<String, Any?>(
                "quantity" to (cartItem.quantity?.let { it + if (increase) 1 else -1 })
            )
        )
    }

    fun removeProductFromCart(cartItem: ProductWithQuantity) {
        ref.child(cartItem.id?:"").setValue(null)
    }

    fun getSelectedCartItemIds(): List<String> {
        return _productsWithQuantities.filter { it.selected?: false }.mapNotNull { it.id }
    }

}