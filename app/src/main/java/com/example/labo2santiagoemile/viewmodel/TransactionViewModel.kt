package com.example.labo2santiagoemile.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.AndroidViewModel
import com.example.labo2santiagoemile.model.TransactionRepository
import com.example.labo2santiagoemile.model.Transaction


class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionRepository = TransactionRepository(application)


    var transactions by mutableStateOf(transactionRepository.loadTransactions())
        private set


    fun addTransaction(transaction: Transaction) {
        transactions = transactions + transaction
        transactionRepository.saveTransactions(transactions)
    }


    fun updateTransaction(updatedTransaction: Transaction) {
        transactions =
            transactions.map { if (it.id == updatedTransaction.id) updatedTransaction else it }
        transactionRepository.saveTransactions(transactions)
    }


    fun deleteTransaction(transactionId: Int) {
        transactions = transactions.filter { it.id != transactionId }
        transactionRepository.saveTransactions(transactions)
    }

    fun sortTransactionsByType(asc: Boolean) {
        val listTypes = listOf("Depense", "Revenu")
        transactions = if (asc) {
            transactions.sortedBy { listTypes.indexOf(it.type) }
        } else {
            transactions.sortedByDescending { listTypes.indexOf(it.type) }
        }
        transactionRepository.saveTransactions(transactions)
    }

}
