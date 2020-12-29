package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import com.example.fibonaccinumber.MainContract

class Repository(private val sharedPreferences: SharedPreferences) : MainContract.MainRepository {

    private var currentIndex: Int
    private var currentEnterNumber: String
    private var errorType: String

    init {
        currentIndex = sharedPreferences.getInt(STATE_INT_INDEX, 0)
        currentEnterNumber = ""
        errorType = STR_MESSAGE_TYPE
    }

    override fun setCurrentIndex(currentIndex: Int) {
        this.currentIndex = currentIndex
    }

    override fun setCurrentEnterNumber(currentEnterNumber: String) {
        this.currentEnterNumber = currentEnterNumber
    }

    override fun setErrorType(errorType: String) {
        this.errorType = errorType
    }

    override fun getCurrentIndex(): Int = currentIndex

    override fun getCurrentEnterNumber(): String = currentEnterNumber

    override fun getErrorType(): String = errorType

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