package com.example.labo2santiagoemile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.labo2santiagoemile.model.Transaction
import com.example.labo2santiagoemile.viewmodel.TransactionViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsListScreen(transactionViewModel: TransactionViewModel, navController: NavController) {
    var sortAscending by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    transactionViewModel.sortTransactionsByType(sortAscending)
                    sortAscending = !sortAscending
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                val label = if (sortAscending) "Sort by Spendings" else "Sort by Savings"
                Text(label)
            }

            Button(
                onClick = {

                    navController.navigate("TransactionDetailsScreen")
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Ajouter une transaction")
            }

            Button(
                onClick = {

                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("retourner")
            }


            LazyColumn {
                items(transactionViewModel.transactions) { transaction ->
                    TransactionRow(
                        transaction = transaction,
                        onEditClick = {
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
}

@Composable
fun TransactionRow(transaction: Transaction, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(transaction.title, modifier = Modifier.weight(1f))
        if(transaction.type == "Depense"){
            Text("$${transaction.amount}",
                modifier = Modifier.weight(1f),
                color = Color.Red
            )
        }else if(transaction.type == "Revenu"){
            Text("$${transaction.amount}",
                modifier = Modifier.weight(1f),
                color = Color.Green
            )
        }


        Text(transaction.categorie, modifier = Modifier.weight(1f))
        //fait bug le programme ma theorie cest parce que pas de place
//        Text(transaction.date, modifier = Modifier.weight(1f))


        Column(
            modifier = Modifier
        ) {
            Button(onClick = onEditClick) {
                Text("Details")
            }
            Button(onClick = onDeleteClick) {
                Text("Delete")
            }
        }
    }
}
