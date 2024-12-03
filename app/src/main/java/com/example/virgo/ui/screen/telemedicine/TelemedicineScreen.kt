package com.example.virgo.ui.screen.telemedicine

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.virgo.R
import com.example.virgo.model.lib.Message
import com.example.virgo.ui.theme.ColorBackground
import com.example.virgo.ui.theme.ColorGradient1
import com.example.virgo.viewModel.TelemedicineViewModel
import com.google.firebase.Timestamp

@Composable
fun TelemedicineScreen () {
    val viewModel: TelemedicineViewModel = viewModel()
    val messageList = viewModel.messageList.value
    val input = viewModel.input.value

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            LazyColumn (
                reverseLayout = true,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                items(messageList) { msg ->
                    Message(msg = msg)
                }
            }
        }
        UserInput(
            input = input,
            onChangeInput = { viewModel.onChangeInput(it) },
            onSend = { viewModel.onSendText() }
        ) {
            viewModel.onSelectImage(it)
        }
    }
}

@Composable
fun Message (msg: Message) {
    val backgroundColor = if (msg.user == true) ColorGradient1 else Color.White
    val onBackgroundColor = if (msg.user == true) Color.White else Color.Black
    val align = if (msg.user == true) Arrangement.End else Arrangement.Start

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = align
    ) {
        Box(modifier = Modifier
            .widthIn(max = 300.dp)
            .padding(8.dp)) {
            if (!msg.text.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .background(color = backgroundColor, shape = RoundedCornerShape(10))
                        .padding(10.dp)
                ) {
                    Text(text = msg.text, color = onBackgroundColor)
                }
            } else {
                AsyncImage(
                    model = msg.image.toString(),
                    contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput (input: String, onChangeInput: (String) -> Unit, onSend: () -> Unit, onSelectImages: (List<Uri>) -> Unit) {

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) {
        onSelectImages(it)
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            multiplePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = null)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .background(color = Color.White, shape = RoundedCornerShape(50))
        ) {
            TextField(
                value = input,
                onValueChange = onChangeInput,
                placeholder = { Text(text = "Messgae") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        IconButton(onClick = { onSend() }) {
            Icon(imageVector = Icons.Filled.Send, contentDescription = null)
        }
    }
}