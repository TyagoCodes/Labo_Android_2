package com.example.labo2santiagoemile.model

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TransactionRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("transaction_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadTransactions(): List<Transaction> {
        val json = sharedPreferences.getString("transactions", null)
        return if (json != null) {
            val type = object : TypeToken<List<Transaction>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveTransactions(transactions: List<Transaction>) {
        val json = gson.toJson(transactions)
        sharedPreferences.edit().putString("transactions", json).apply()
    }
}
