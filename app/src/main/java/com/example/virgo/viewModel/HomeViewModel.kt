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

    val articleList: State<List<Article>>
        get() = derivedStateOf {
        _articleList.toList()
    }
    val productList : State<List<Product>>
        get() = derivedStateOf {
            _productList.toList()
        }

    fun fetchArticle_And_Product() {
        _articleList.clear()
        _productList.clear()
        FirebaseFirestore.getInstance().collection("articles").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val article = doc.toObject<Article>()
                _articleList.add(article)
            }
        }

        FirebaseFirestore.getInstance().collection("products").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val product = doc.toObject<Product>()
                _productList.add(product)
            }
        }
    }
}