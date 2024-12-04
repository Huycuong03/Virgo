package com.example.virgo.ui.screen.ecommerce

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.virgo.ui.theme.VirgoTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPrescriptionScreen(navController: NavController) {
    var prescriptionText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Prescription") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) {  padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter your prescription details below.",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = prescriptionText,
                onValueChange = { prescriptionText = it },
                label = { Text("Prescription") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Provide more space for longer text
                placeholder = { Text("Type your prescription here...") },
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Handle the submission logic here, e.g., sending the prescription string to the server
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Prescription")
            }
        }
    }
}
