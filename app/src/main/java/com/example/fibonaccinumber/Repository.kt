package com.example.fibonaccinumber

import android.content.SharedPreferences

class Repository(private val sharedPreferences: SharedPreferences) {

    private val editState = sharedPreferences.edit()

    fun saveState(currentIndex: Int, currentTextNumber: String) {
        saveIndex(currentIndex)
        saveTextNumber(currentTextNumber)
        editState.apply()
    }

    fun loadIndex(): Int = sharedPreferences.getInt(PREF_INT_INDEX, 0)

    fun loadTextNumber(): String = sharedPreferences.getString(PREF_STR_NUMBER, "") ?: ""

    private fun saveIndex(currentIndex: Int) {
        editState.putInt(PREF_INT_INDEX, currentIndex)
    }

    private fun saveTextNumber(currentTextNumber: String){
        editState.putString(PREF_STR_NUMBER, currentTextNumber)
    }

    private companion object{
        private const val PREF_INT_INDEX = "CurrentIndex"
        private const val PREF_STR_NUMBER = "CurrentTextNumber"
    }
}