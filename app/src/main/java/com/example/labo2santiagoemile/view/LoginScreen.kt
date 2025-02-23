package com.example.labo2santiagoemile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labo2santiagoemile.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color.Blue
            ),
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color.Blue
            ),
            label = { Text("Mot de Passe") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
                if (authViewModel.isLoggedIn) {
                    navController.navigate("home")
                } else {
                    error = true
                }
            },
            modifier = Modifier
        ) {
            Text("Se Connecter")
        }

        if (error) Text("Erreur d'email ou mot de passe", color = MaterialTheme.colorScheme.error)
    }
}