package com.example.virgo.ui.screen.lib

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavIcon ( icon: ImageVector? = null, onNav: () -> Unit ) {
    IconButton(onClick = { onNav() }) {
        Icon(imageVector = icon?: Icons.Filled.ArrowBack, contentDescription = null)
    }
}