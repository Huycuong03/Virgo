package com.example.virgo.ui.screen.lib

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.virgo.ui.theme.ColorAccent
import com.example.virgo.ui.theme.ColorDisabled

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagSection (values: List<String>, onClick: (String) -> Unit) {
    FlowRow (horizontalArrangement = Arrangement.spacedBy(10.dp),) {
        values.forEach { value ->
            Tag(value, onClick = onClick)
        }
    }
}

@Composable
fun Tag (value: String, onClick: (String) -> Unit) {
    TextButton(
        onClick = { onClick(value) },
        colors = ButtonColors(ColorAccent, Color.White, ColorDisabled, Color.DarkGray)
    ) {
        Text(text = value)
    }
}