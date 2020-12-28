package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.example.fibonaccinumber.MainContract

class Repository(sharedPreferences: SharedPreferences) : MainContract.MainRepository {

    private var currentIndex: Int = sharedPreferences.getInt(STATE_INT_INDEX, 0)
    private var currentEnterNumber: String = ""
    private var errorMessage: String = ""
    private var errorType: String = STR_ERROR_TYPE

    private var editState = sharedPreferences.edit()
    private var outState: Bundle? = null

    override fun setOutState(outState: Bundle?) {
        this.outState = outState
    }

    override fun setCurrentIndex(currentIndex: Int) {
        this.currentIndex = currentIndex
    }

    override fun setCurrentEnterNumber(currentEnterNumber: String) {
        this.currentEnterNumber = currentEnterNumber
    }

    override fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    override fun setErrorType(errorType: String) {
        this.errorType = errorType
    }

    override fun getCurrentIndex(): Int = currentIndex

    override fun getCurrentEnterNumber(): String = currentEnterNumber

    override fun getErrorMessage(): String = errorMessage

    override fun getErrorType(): String = errorType

    override fun saveState() {
        outState?.putInt(STATE_INT_INDEX, currentIndex)
        outState?.putString(STATE_STR_ENTER_NUMBER, currentEnterNumber)
        outState?.putString(STATE_STR_ERR_MESSAGE, errorMessage)
        outState?.putString(STATE_STR_ERR_TYPE, errorType)

        editState.putInt(STATE_INT_INDEX, currentIndex)
        editState.apply()
    }

    override fun setStateFromBundle(savedInstantState: Bundle) {
        val newIndex = savedInstantState.getInt(STATE_INT_INDEX)
        val newEnterNumber = savedInstantState.getString(STATE_STR_ENTER_NUMBER) ?: ""
        val newErrorMessage = savedInstantState.getString(STATE_STR_ERR_MESSAGE) ?: ""
        val newErrorType = savedInstantState.getString(STATE_STR_ERR_TYPE) ?: STR_ERROR_TYPE

        setCurrentIndex(newIndex)
        setCurrentEnterNumber(newEnterNumber)
        setErrorMessage(newErrorMessage)
        setErrorType(newErrorType)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getOutState(): Bundle? = outState

    private companion object {
        private const val STATE_INT_INDEX = "currentIndex"
        private const val STATE_STR_ENTER_NUMBER = "currentEnterNumber"
        private const val STATE_STR_ERR_MESSAGE = "errorMessage"
        private const val STATE_STR_ERR_TYPE = "errorType"

        private const val STR_ERROR_TYPE = "CORRECT"
    }
}