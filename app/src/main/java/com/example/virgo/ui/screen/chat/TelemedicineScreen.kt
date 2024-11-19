package com.example.virgo.ui.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.virgo.R
import com.example.virgo.model.Message
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelemedicineScreen () {
    val msgList = listOf(
        Message(
            timestamp = Timestamp.now(),
            text = "Hello there!"
        ),
        Message(
            isUser = false,
            timestamp = Timestamp.now(),
            text = "How can I help you?"
        ),
        Message(timestamp = Timestamp.now())
    )
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        MyBar(
            title = "Telemedicine",
            navIcon = {
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(msgList) { msg ->
                Message(msg = msg)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        UserInput()
    }
}

@Composable
fun Message (msg: Message) {
    val backgroundColor = if (msg.isUser) ColorGradient1 else ColorBackground
    val onBackgroundColor = if (msg.isUser) Color.White else Color.Black
    val align = if (msg.isUser) Arrangement.End else Arrangement.Start

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = align
    ) {
        if (!msg.text.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .background(color = backgroundColor, shape = RoundedCornerShape(50))
                    .padding(10.dp)
            ) {
                Text(text = msg.text, color = onBackgroundColor)
            }
        } else {
            Image(painter = painterResource(id = R.drawable.image_holder), contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput () {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .background(color = Color.White, shape = RoundedCornerShape(50))
        ) {
            TextField(
                value = "",
                onValueChange = {  },
                placeholder = { Text(text = "Messgae") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Transparent background
                    focusedIndicatorColor = Color.Transparent, // Optional: make focused indicator transparent
                    unfocusedIndicatorColor = Color.Transparent // Optional: make unfocused indicator transparent
                )
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Send, contentDescription = null)
        }
    }
}

@Composable
fun MyBar (
    title: String? = null,
    navIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        navIcon()
        if (!title.isNullOrEmpty()) {
            Text(text = title)
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            actions()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelemedicineScreen() {
    TelemedicineScreen()
}