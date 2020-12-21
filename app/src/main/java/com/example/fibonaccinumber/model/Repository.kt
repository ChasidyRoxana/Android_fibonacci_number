package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import android.os.Bundle

class Repository(sharedPreferences: SharedPreferences) {

    var currentIndex: Int = sharedPreferences.getInt(STATE_INT_INDEX, 0)
        private set
    var currentEnterNumber: String = ""
        private set
    var errorMessage: String = ""
        private set

    private val editState = sharedPreferences.edit()
    private var outState: Bundle? = null

    fun setOutState(outState: Bundle?) {
        this.outState = outState
    }

    fun saveIndex(currentIndex: Int) {
        this.currentIndex = currentIndex
        outState?.putInt(STATE_INT_INDEX, currentIndex)
    }

    fun saveEnterNumber(currentEnterNumber: String) {
        this.currentEnterNumber = currentEnterNumber
        outState?.putString(STATE_STR_NUMBER, currentEnterNumber)
    }

    fun saveErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
        outState?.putString(STATE_STR_ERROR, errorMessage)
    }

    fun saveSharedPref() {
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