package com.example.virgo.viewModel


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.virgo.model.ecommerce.Product
import com.example.virgo.model.lib.Article
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SearchViewModel : ViewModel() {
    private val _productList = mutableListOf<Product>()
    private val _articleList = mutableListOf<Article>()

    private val _searchText = mutableStateOf("")
    private val _productResultList = mutableStateListOf<Product>()
    private val _articleResultList = mutableStateListOf<Article>()
    private val _recentSearches = mutableStateListOf<String>()
    private val _frequentSearches = mutableStateListOf<String>()
    private val _addedProductID = mutableStateListOf<String>()


    val searchText: State<String> get() = _searchText
    val recentSearches: State<List<String>> get() = derivedStateOf {
        _recentSearches.toList()
    }
    val frequentSearches: State<List<String>> get() = derivedStateOf {
        _frequentSearches.toList()
    }
    val productResultList: State<List<Product>> get() = derivedStateOf {
        _productResultList.toList()
    }
    val articleResultList: State<List<Article>> get() = derivedStateOf {
        _articleResultList.toList()
    }
    val addedProductID : State<List<String>> get() = derivedStateOf {
        _addedProductID.toList()
    }
    init {
        val db = FirebaseFirestore.getInstance()
        val ref = FirebaseDatabase.getInstance("https://virgo-238d4-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("searches")
        ref.child("hUMo6tRvQ6XcLhDkBOV9A1w6Jvr2").limitToFirst(5).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    _recentSearches.add(it.getValue<String>().toString())
                }
                Log.d("searchList", _recentSearches.joinToString())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        ref.child("trending").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    _frequentSearches.add(it.getValue<String>().toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        db.collection("products").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val product = doc.toObject<Product>()
                _productList.add(product)
            }
        }

        db.collection("articles").get().addOnSuccessListener { documents ->
            for (doc in documents) {
                val article = doc.toObject<Article>()
                _articleList.add(article)
            }
        }
    }

    fun onChangeSearchText(keyword: String) {
        _searchText.value = keyword
        _productResultList.clear()
        _articleResultList.clear()

        for (product in _productList) {
            if (product.name?.contains(keyword, ignoreCase = true) == true) {
                _productResultList.add(product)
            }
        }

        for (article in _articleList) {
            if (article.name?.contains(keyword, ignoreCase = true) == true) {
                _articleResultList.add(article)
            }
        }
    }
    fun addProduct(product: Product){
        product.id?.let { _addedProductID.add(it) }
    }
}