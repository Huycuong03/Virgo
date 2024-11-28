package com.example.virgo.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ProductDetailViewModel : ViewModel() {

    // MutableState for Compose
    private val _product = mutableStateOf(Product())

    val product : State<Product> get() = _product

    // Fetch product data from Firestore
    fun fetchProduct(productId: String) {
        FirebaseFirestore.getInstance().collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                document.toObject<Product>()?.let{
                    _product.value = it
                }
            }
            .addOnFailureListener {
                Log.e("DetailViewModel", "Error fetching product", it)
            }
    }
}