package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import android.os.Bundle

class Repository(private val sharedPreferences: SharedPreferences) {

    var currentIndex: Int = sharedPreferences.getInt(STATE_INT_INDEX, 0)
        private set
    var currentEnterNumber: String = ""
        private set
    var errorMessage: String = ""
        private set

    fun saveState(
        outState: Bundle,
        currentIndex: Int,
        currentEnterNumber: String,
        errorMessage: String
    ) {
        outState.putInt(STATE_INT_INDEX, currentIndex)
        outState.putString(STATE_STR_NUMBER, currentEnterNumber)
        outState.putString(STATE_STR_ERROR, errorMessage)

        val editState = sharedPreferences.edit()
        editState.putInt(STATE_INT_INDEX, currentIndex)
        editState.apply()
    }

    fun setState(savedInstantState: Bundle) {
        currentIndex = savedInstantState.getInt(STATE_INT_INDEX)
        currentEnterNumber = savedInstantState.getString(STATE_STR_NUMBER) ?: ""
        errorMessage = savedInstantState.getString(STATE_STR_ERROR) ?: ""
    }

    private companion object {
        private const val STATE_INT_INDEX = "currentIndex"
        private const val STATE_STR_NUMBER = "currentEnterNumber"
        private const val STATE_STR_ERROR = "errorMessage"
    }
}