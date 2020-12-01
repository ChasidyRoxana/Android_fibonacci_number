package com.example.fibonaccinumber

import android.content.SharedPreferences

class Repository(private val sharedPreferences: SharedPreferences) {

    val currentIndex = sharedPreferences.getInt(PREF_INT_INDEX, 0)
    val currentTextNumber = sharedPreferences.getString(PREF_STR_NUMBER, "") ?: ""

    fun saveState(currentIndex: Int, currentTextNumber: String) {
        val editState = sharedPreferences.edit()
        editState.putInt(PREF_INT_INDEX, currentIndex)
        editState.putString(PREF_STR_NUMBER, currentTextNumber)
        editState.apply()
    }

    private companion object {
        private const val PREF_INT_INDEX = "currentIndex"
        private const val PREF_STR_NUMBER = "currentTextNumber"
    }
}