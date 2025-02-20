package com.example.labo2santiagoemile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labo2santiagoemile.model.Transaction
import com.example.labo2santiagoemile.viewmodel.TransactionViewModel

@Composable
fun TransactionsListScreen(transactionViewModel: TransactionViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Bouton pour ajouter une nouvelle transaction
        Button(
            onClick = {
                // Navigate to TransactionDetailsScreen for adding a new transaction
                navController.navigate("TransactionDetailsScreen")
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Ajouter une transaction")
        }

        // Liste des transactions
        LazyColumn {
            items(transactionViewModel.transactions) { transaction ->
                TransactionRow(
                    transaction = transaction,
                    onEditClick = {
                        // Navigate to TransactionDetailsScreen with the transaction id to edit it
                        navController.navigate("TransactionDetailsScreen/${transaction.id}")
                    },
                    onDeleteClick = {
                        transactionViewModel.deleteTransaction(transaction.id)
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionRow(transaction: Transaction, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(transaction.title, modifier = Modifier.weight(1f))
        Text("$${transaction.amount}", modifier = Modifier.weight(1f))
        Button(onClick = onEditClick) {
            Text("Edit")
        }
        Button(onClick = onDeleteClick) {
            Text("Delete")
        }
    }
}
