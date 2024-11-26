package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.virgo.model.ecommerce.ProductWithQuantity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.virgo.model.ecommerce.Product

class CartViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _productsWithQuantities = mutableStateListOf<ProductWithQuantity>()
    private val _totalSum = mutableDoubleStateOf(0.0)
    private val _selectAllChecked = mutableStateOf(false)

    val productsWithQuantities: State<List<ProductWithQuantity>>
        get() = derivedStateOf {
            _productsWithQuantities.toList()
        }
    val totalSum: State<Double> get() = _totalSum
    val selectAllChecked: State<Boolean> get() = _selectAllChecked

    init {
        fetchProductsFromFirestore()
    }

    private fun fetchProductsFromFirestore() {
        firestore.collection("users").document("hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2")
            .get()
            .addOnSuccessListener { document ->
                // Check if document exists and contains a cart
                if (document.exists()) {
                    val cartList = document.get("cart") as? List<Map<String, Any>>
                    if (cartList != null) {
                        val fetchedProducts = cartList.mapNotNull { cartItem ->
                            val product = cartItem["product"] as? Map<String, Product>
                            val quantity = (cartItem["quantity"] as? Long)?.toInt() ?: 1

                            // Convert product data to Product class
                            product?.let {
                                ProductWithQuantity(
                                    product = Product(
                                        id = it["id"] as? String,
                                        name = it["name"] as? String,
                                        price = (it["price"] as? Double)?.toFloat(),
                                        images = (it["images"] as? List<String>).orEmpty(),
                                    ),
                                    quantity = quantity
                                )
                            }
                        }

                        // Update the products list
                        _productsWithQuantities.clear()
                        _productsWithQuantities.addAll(fetchedProducts)
                        calculateTotalSum()
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching products: ${exception.message}")
            }
    }

    private fun calculateTotalSum() {
        _totalSum.value = _productsWithQuantities.filter { it.selected == true }.sumOf {
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

    fun increaseQuantity(productId: String) {
        _productsWithQuantities.map { productWithQuantity ->
            if (productWithQuantity.product?.id == productId) {
                val updatedProduct = productWithQuantity.copy(quantity = productWithQuantity.quantity?.plus(1))
                _productsWithQuantities[_productsWithQuantities.indexOf(productWithQuantity)] = updatedProduct
            }
        }
        calculateTotalSum()
    }

    // Decrease quantity for a product
    fun decreaseQuantity(productId: String) {
        _productsWithQuantities.map { productWithQuantity ->
            if (productWithQuantity.product?.id == productId && productWithQuantity.quantity ?: 0 > 1) {
                val updatedProduct = productWithQuantity.copy(quantity = productWithQuantity.quantity?.minus(1))
                _productsWithQuantities[_productsWithQuantities.indexOf(productWithQuantity)] = updatedProduct
            }
        }
        calculateTotalSum()
    }
}
