package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virgo.ui.theme.ColorBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar (
    query: String,
    placeholder: String = "Search for products, articles, ...",
    trailingIcon: @Composable () -> Unit = {},
    onChange: (String) -> Unit,
    onDone: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColorBackground, shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp)
    ) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        TextField(
            value = query,
            onValueChange = onChange,
            placeholder = { Text(text = placeholder) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Transparent background
                focusedIndicatorColor = Color.Transparent, // Optional: make focused indicator transparent
                unfocusedIndicatorColor = Color.Transparent // Optional: make unfocused indicator transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        trailingIcon()
    }
}