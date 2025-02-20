package com.example.labo2santiagoemile.view

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labo2santiagoemile.viewmodel.AuthViewModel
import com.example.labo2santiagoemile.viewmodel.TransactionViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel =
        viewModel(factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application))
    val transactionViewModel: TransactionViewModel =
        viewModel(factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application))

    NavHost(navController, startDestination = if (authViewModel.isLoggedIn) "home" else "login") {
        composable("login") { LoginScreen(authViewModel, navController) }
        composable("home") { HomeScreen(authViewModel, navController) }
        composable("TransactionsListScreen") { TransactionsListScreen(transactionViewModel, navController) }
        composable("AddTransactionScreen/{transactionId}") { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")?.toIntOrNull()
            if (transactionId != null) {
                AddTransactionScreen(transactionViewModel, navController)
            } else {
                Text("Transaction not found", modifier = Modifier.padding(16.dp))
            }
        }
    }
}