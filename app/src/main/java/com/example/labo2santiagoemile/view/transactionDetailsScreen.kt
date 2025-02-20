package com.example.labo2santiagoemile.view

import androidx.compose.foundation.layout.*
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
fun TransactionDetailsScreen(
    transactionViewModel: TransactionViewModel,
    navController: NavController,
    transactionId: Int? = null // L'ID de la transaction si l'on modifie une transaction existante
) {
    // État pour les champs de saisie
    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }

    // Si l'ID de la transaction est fourni, nous sommes en mode "modifier"
    if (transactionId != null) {
        val transaction = transactionViewModel.transactions.find { it.id == transactionId }
        if (transaction != null) {
            // Remplir les champs avec les données de la transaction
            transactionTitle = transaction.title
            transactionAmount = transaction.amount.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Champ pour le titre de la transaction
        TextField(
            value = transactionTitle,
            onValueChange = { transactionTitle = it },
            label = { Text("Titre de la transaction") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ pour le montant de la transaction
        TextField(
            value = transactionAmount,
            onValueChange = { transactionAmount = it },
            label = { Text("Montant de la transaction") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour enregistrer la transaction
        Button(
            onClick = {
                if (transactionTitle.isNotBlank() && transactionAmount.isNotBlank()) {
                    val newTransaction = if (transactionId != null) {
                        // Modifier la transaction existante
                        val updatedTransaction = transactionViewModel.transactions.find { it.id == transactionId }
                        updatedTransaction?.copy(
                            title = transactionTitle,
                            amount = transactionAmount.toDouble()
                        )
                    } else {
                        // Ajouter une nouvelle transaction
                        Transaction(
                            id = transactionViewModel.transactions.size + 1,
                            title = transactionTitle,
                            amount = transactionAmount.toDouble(),
                            date = "2025-02-20" // Remplacez par la date actuelle si nécessaire
                        )
                    }

                    if (newTransaction != null) {
                        if (transactionId != null) {
                            transactionViewModel.updateTransaction(newTransaction)
                        } else {
                            transactionViewModel.addTransaction(newTransaction)
                        }
                    }

                    // Retourner à l'écran de liste des transactions
                    navController.navigate("TransactionsListScreen") {
                        popUpTo("TransactionDetailsScreen") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (transactionId != null) "Modifier la transaction" else "Ajouter la transaction")
        }
    }
}
