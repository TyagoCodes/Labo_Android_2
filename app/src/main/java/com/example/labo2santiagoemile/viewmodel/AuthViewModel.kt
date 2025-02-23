package com.example.labo2santiagoemile.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

var usernameGlob = ""

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences: SharedPreferences by lazy {
        application.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    var isLoggedIn by mutableStateOf(sharedPreferences.getBoolean("isLoggedIn", false))
        private set

    fun login(username: String, password: String) {

        if (username == "user@example.com" && password == "password123") {
            usernameGlob = username
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            isLoggedIn = true
        } else {
            println("Invalid credentials")
            isLoggedIn = false
        }
    }

    fun logout() {
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
        isLoggedIn = false
    }
}
