package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import com.example.fibonaccinumber.MainContract

class Repository(private val sharedPreferences: SharedPreferences) : MainContract.MainRepository {

    override var currentIndex: Int = sharedPreferences.getInt(STATE_INT_INDEX, 0)
    override var currentEnterNumber: String = ""
    override var messageType: String = STR_MESSAGE_TYPE


    override fun saveState() {
        val editState = sharedPreferences.edit()

        editState.putInt(STATE_INT_INDEX, currentIndex)
        editState.apply()
    }

    private companion object {
        private const val STATE_INT_INDEX = "currentIndex"

        private const val STR_MESSAGE_TYPE = "CORRECT"
    }
}