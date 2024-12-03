package com.example.virgo.viewModel.ecommerce

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.User
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ProductDetailViewModel : ViewModel() {

    private val _product = mutableStateOf(Product())
    private val _selectedQuantity = mutableIntStateOf(1)
    private val db = FirebaseFirestore.getInstance().collection("products")
    private val ref = FirebaseDatabase.getInstance(SharedPreferencesManager.getString("firebase_database")?:"").getReference("cart").child(SharedPreferencesManager.getUID())

    val product : State<Product> get() = _product
    val selectedQuantity: State<Int> get() = _selectedQuantity

    fun fetchProduct(productId: String) {
        db.document(productId)
            .get()
            .addOnSuccessListener { document ->
                document.toObject<Product>()?.let {
                    _product.value = it.copy(id = productId)
                }
            }
    }

    fun addProductToCart(callback: () -> Unit) {
        ref.get().addOnSuccessListener {snapshot ->
            val cart = mutableListOf<ProductWithQuantity>()
            for (cartItem in snapshot.children) {
                cartItem.getValue<ProductWithQuantity>()?.let {
                    cart.add(it.copy(id = cartItem.key))
                }
            }
            val existingCartItem = cart.find { it.product?.id == _product.value.id }

            if (existingCartItem == null) {
                val newCartItem = ProductWithQuantity(
                    product = _product.value,
                    quantity = _selectedQuantity.intValue
                )
                ref.push().setValue(newCartItem)
                    .addOnSuccessListener { callback() }
            } else {
                Log.d("cartItemId", existingCartItem.id.toString())
                ref.child(existingCartItem.id?:"").updateChildren(
                    hashMapOf<String, Any?>(
                        "quantity" to (existingCartItem.quantity?.let{ it + _selectedQuantity.intValue})
                    )
                )
                    .addOnSuccessListener { callback() }
            }
        }
    }
}