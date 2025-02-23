package com.example.labo2santiagoemile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    transactionViewModel: TransactionViewModel,
    navController: NavController,
    transactionId: Int? = null
) {

    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf("") }
    var transactionCategorie by remember { mutableStateOf("") }

    if (transactionId != null) {
        val transaction = transactionViewModel.transactions.find { it.id == transactionId }
        if (transaction != null) {

            transactionTitle = transaction.title
            transactionAmount = transaction.amount.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {


            Button(
                onClick = {

                    navController.navigate("transactionsListScreen")
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Retourner")
            }

            TextField(
                value = transactionTitle,
                onValueChange = { transactionTitle = it },
                label = { Text("Titre de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            TextField(
                value = transactionAmount,
                onValueChange = { transactionAmount = it },
                label = { Text("Montant de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            TextField(
                value = transactionType,
                onValueChange = { transactionType = it },
                label = { Text("Type de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = transactionCategorie,
                onValueChange = { transactionCategorie = it },
                label = { Text("Categorie de la transaction") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (transactionTitle.isNotBlank() && transactionAmount.isNotBlank()) {
                        val newTransaction = if (transactionId != null) {

                            val updatedTransaction = transactionViewModel.transactions.find { it.id == transactionId }
                            updatedTransaction?.copy(
                                title = transactionTitle,
                                amount = transactionAmount.toDouble()
                            )
                        } else {

                            Transaction(
                                id = transactionViewModel.transactions.size + 1,
                                title = transactionTitle,
                                amount = transactionAmount.toDouble(),
                                type = transactionType,
                                categorie = transactionCategorie
                            )
                        }

                        if (newTransaction != null) {
                            if (transactionId != null) {
                                transactionViewModel.updateTransaction(newTransaction)
                            } else {
                                transactionViewModel.addTransaction(newTransaction)
                            }
                        }


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
}
