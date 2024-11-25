package com.example.virgo.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.virgo.route.HomeRoute
import com.example.virgo.route.SignupRoute
import com.example.virgo.ui.theme.ColorError
import com.example.virgo.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()
    val email = viewModel.email.value
    val password = viewModel.password.value
    var error by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log In", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        BasicTextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    innerTextField()
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = error, color = ColorError)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { 
                viewModel.onLogin( onSuccess = { navController.navigate(HomeRoute) } ) {
                    error = it
                }
            }
        ) {
            Text("Log In")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate(SignupRoute) }) {
            Text("Don't have an account? Sign Up")
        }
    }
}