package com.example.virgo.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.example.virgo.repository.SharedPreferencesManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ProductDetailViewModel : ViewModel() {

    // MutableState for Compose
    private val _product = mutableStateOf(Product())
    private val db = FirebaseFirestore.getInstance()

    val product : State<Product> get() = _product

    // Fetch product data from Firestore
    fun fetchProduct(productId: String) {
        db.collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                document.toObject<Product>()?.let {
                    _product.value = it
                }
            }
            .addOnFailureListener {
                Log.e("DetailViewModel", "Error fetching product", it)
            }
    }
//    fun addProductToCart() {
//        val userId = SharedPreferencesManager.getString("uid") ?: ""
//        if (userId.isEmpty()) {
//            Log.e("ProductDetailViewModel", "User ID is empty.")
//            return
//        }
//
//        val cartItem = ProductWithQuantity(
//            product = _product.value,
//            quantity = 1
//        )
//
//        // Convert ProductWithQuantity to a Map
//        val cartItemMap = mapOf(
//            "product" to cartItem.product, // Assuming product has the necessary structure
//            "quantity" to cartItem.quantity
//        )
//
//        // Update the cart in Firestore
//        db.collection("users")
//            .document(userId)
//            .update("cart", FieldValue.arrayUnion(cartItemMap))
//            .addOnSuccessListener {
//                Log.d("ProductDetailViewModel", "Product added to cart successfully")
//            }
//            .addOnFailureListener { exception ->
//                Log.e("ProductDetailViewModel", "Failed to add product to cart", exception)
//            }
//    }

    fun addProductToCart() {
        val cartItem = ProductWithQuantity(
            product = _product.value,
            quantity = 1
        )

        val userId = SharedPreferencesManager.getString("uid") ?: ""

        // Reference to the user's Firestore document
        val userDocRef = db.collection("users").document(userId)

        // Fetch the current cart from Firestore
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val cartList = document.get("cart") as? List<Map<String, Any>> ?: emptyList()

                    // Check if the product already exists in the cart
                    val existingProduct = cartList.find {
                        // Assuming the product field contains a map with an id
                        val product = it["product"] as? Map<String, Any>
                        product?.get("id") == cartItem.product?.id
                    }

                    if (existingProduct != null) {
                        // Product exists, update the quantity
                        val updatedCartList = cartList.map {
                            val product = it["product"] as? Map<String, Any>
                            if (product?.get("id") == cartItem.product?.id) {
                                val updatedQuantity = (it["quantity"] as? Long)?.toInt()?.plus(1) ?: 1
                                it.toMutableMap().apply { put("quantity", updatedQuantity) }
                            } else {
                                it
                            }
                        }

                        // Update the cart in Firestore
                        userDocRef.update("cart", updatedCartList)
                            .addOnSuccessListener {
                                Log.d("ProductDetailViewModel", "Cart updated successfully")
                            }
                            .addOnFailureListener {
                                Log.e("ProductDetailViewModel", "Failed to update cart", it)
                            }
                    } else {
                        // Product doesn't exist, add a new product to the cart
                        userDocRef.update("cart", FieldValue.arrayUnion(mapOf(
                            "product" to cartItem.product,
                            "quantity" to cartItem.quantity
                        )))
                            .addOnSuccessListener {
                                Log.d("ProductDetailViewModel", "Product added to cart successfully")
                            }
                            .addOnFailureListener {
                                Log.e("ProductDetailViewModel", "Failed to add product to cart", it)
                            }
                    }
                }
            }
            .addOnFailureListener {
                Log.e("ProductDetailViewModel", "Error fetching user data", it)
            }
    }
}