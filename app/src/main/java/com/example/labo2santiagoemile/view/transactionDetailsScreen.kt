package com.example.labo2santiagoemile.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
    var transactionDate by remember { mutableStateOf("") }


    var typeIsExpanded by remember { mutableStateOf(false) }
    var catIsExpanded by remember { mutableStateOf(false) }

    val listTypes = listOf("Depense", "Revenu")
    val listCategories = listOf("Alimentation", "Transport", "Logement", "Loisirs", "Savings")

    var typeFieldSize by remember { mutableStateOf(Size.Zero) }
    var catFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (typeIsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

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
                title = { Text("Details") }
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


            //type dropdown
            OutlinedTextField(
                value = transactionType,
                onValueChange = { transactionType = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        typeFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Type de la transaction") },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier.clickable { typeIsExpanded = !typeIsExpanded }
                    )
                }
            )

            DropdownMenu(
                expanded = typeIsExpanded,
                onDismissRequest = { typeIsExpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { typeFieldSize.width.toDp() })
            ) {
                listTypes.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            transactionType = label
                            typeIsExpanded = false
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(
                value = transactionCategorie,
                onValueChange = { transactionCategorie = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        catFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Categorie") },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier.clickable { catIsExpanded = !catIsExpanded }
                    )
                }
            )

            DropdownMenu(
                expanded = catIsExpanded,
                onDismissRequest = { catIsExpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { catFieldSize.width.toDp() })
            ) {
                listCategories.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            transactionCategorie = label
                            catIsExpanded = false
                        }
                    )
                }
            }

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
                                categorie = transactionCategorie,
                                date = transactionDate,
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
