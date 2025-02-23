package com.example.labo2santiagoemile.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    fun sortTransactionsByPrice(ascending: Boolean) {
        transactions = if (ascending) {
            transactions.sortedBy { it.amount }
        } else {
            transactions.sortedByDescending { it.amount }
        }
        transactionRepository.saveTransactions(transactions)
    }

}
