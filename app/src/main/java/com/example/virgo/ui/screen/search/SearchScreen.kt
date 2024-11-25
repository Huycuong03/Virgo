package com.example.virgo.ui.screen.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.ui.screen.lib.NavIcon
import com.example.virgo.ui.screen.lib.SearchBar
import com.example.virgo.ui.screen.lib.TopBar
import com.example.virgo.ui.theme.ColorGradient1
import com.example.virgo.viewModel.SearchViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchViewModel = viewModel()
    val recentSearches = viewModel.recentSearches.value
    val frequentSearches = viewModel.frequentSearches.value
    val searchText = viewModel.searchText.value
    val productResultList = viewModel.productResultList.value
    val articleResultList = viewModel.articleResultList.value

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            leadingIcon = {
                NavIcon {
                    navController.popBackStack()
                }
            },
            title = {
                SearchBar(
                    query = searchText,
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onChangeSearchText("") }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                    }
                ) {
                    viewModel.onChangeSearchText(it)
                }
            },
            actions = {}
        )
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {

            if (searchText.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                SearchSuggestion(recentSearches = recentSearches, frequentSearch = frequentSearches) {
                    viewModel.onChangeSearchText(it)
                }
            } else {
                SearchResult(productResultList, articleResultList) { route ->
                    navController.navigate(route)
                }
            }
        }
    }

}

