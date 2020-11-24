package com.example.fibonaccinumber

import android.content.SharedPreferences

class Repository(sharedPreferences: SharedPreferences) {

    private val editState = sharedPreferences.edit()

    val currentIndex = sharedPreferences.getInt(PREF_INT_INDEX, 0)
    val textNumber = sharedPreferences.getString(PREF_STR_NUMBER, "") ?: ""

    fun saveState(currentIndex: Int, currentTextNumber: String) {
        editState.putInt(PREF_INT_INDEX, currentIndex)
        editState.putString(PREF_STR_NUMBER, currentTextNumber)
        editState.apply()
    }

    private companion object {
        private const val PREF_INT_INDEX = "CurrentIndex"
        private const val PREF_STR_NUMBER = "CurrentTextNumber"
    }
}