package com.example.virgo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.lib.Article
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class HomeViewModel : ViewModel() {
    private val _articleList = mutableStateListOf<Article>()
    private val _productList = mutableStateListOf<Product>()

    val articleList: State<List<Article>> get() = derivedStateOf {
        _articleList.toList()
    }
    val productList : State<List<Product>> get() = derivedStateOf {
        _productList.toList()
    }

    init {
        val db = FirebaseFirestore.getInstance()
        db.collection("articles").limit(5).get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val article = doc.toObject<Article>()
                _articleList.add(article)
            }
        }

        db.collection("products").limit(10).get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val product = doc.toObject<Product>().copy(id = doc.id)
                _productList.add(product)
            }
        }
    }
}