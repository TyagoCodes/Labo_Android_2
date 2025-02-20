package com.example.labo2santiagoemile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labo2santiagoemile.model.Transaction
import com.example.labo2santiagoemile.viewmodel.TransactionViewModel

@Composable
fun AddTransactionScreen(
    transactionViewModel: TransactionViewModel,
    navController: NavController
) {
    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = transactionTitle,
            onValueChange = { transactionTitle = it },
            label = { Text("Transaction Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = transactionAmount,
            onValueChange = { transactionAmount = it },
            label = { Text("Transaction Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (transactionTitle.isNotBlank() && transactionAmount.isNotBlank()) {
                    val newTransaction = Transaction(
                        id = transactionViewModel.transactions.size + 1,
                        title = transactionTitle,
                        amount = transactionAmount.toDouble(),
                        date = "2025-02-20"
                    )
                    transactionViewModel.addTransaction(newTransaction)

                    navController.navigate("TransactionsListScreen") {

                        popUpTo("AddTransactionScreen") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Transaction")
        }
    }
}
