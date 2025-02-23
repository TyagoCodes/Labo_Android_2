package com.example.labo2santiagoemile.model

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String,
    val categorie: String,
)
