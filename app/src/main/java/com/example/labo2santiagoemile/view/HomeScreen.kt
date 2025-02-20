package com.example.labo2santiagoemile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.labo2santiagoemile.viewmodel.AuthViewModel

@Composable
fun HomeScreen(authViewModel: AuthViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Bienvenue dans le Gestionnaire de Dépenses", modifier = Modifier.padding(8.dp), fontSize = 28.sp)

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Button(onClick = { navController.navigate("AddTransactionScreen") }) {
            Text("Voir les Transactions")
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Button(onClick = {
            authViewModel.logout()
            navController.navigate("login")
        }) {
            Text("Se Déconnecter")
        }
    }
}