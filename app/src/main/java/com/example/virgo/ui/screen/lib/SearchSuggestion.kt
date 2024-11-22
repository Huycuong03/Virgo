package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchSuggestion (
    recentSearches: List<String>,
    frequentSearch: List<String>,
    onClick: (String) -> Unit
) {
    Column (
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    ) {
        Text(
            text = "Recent search",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if(recentSearches.isEmpty()){
            Text(
                text = "You haven't searched anything yet",
                color = Color.Red
            )
        }
        else{
            recentSearches.forEach { search ->
                TextButton(onClick = { onClick(search) }) {
                    Text(
                        text = search,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Top search",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        TagSection(values = frequentSearch, onClick = onClick)
    }
}