package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.example.fibonaccinumber.MainContract

class Repository(sharedPreferences: SharedPreferences): MainContract.MainRepository {

    private var currentIndex: Int = sharedPreferences.getInt(STATE_INT_INDEX, 0)
    private var currentEnterNumber: String = ""
    private var errorMessage: String = ""

    private var editState = sharedPreferences.edit()
    private var outState: Bundle? = null

    override fun setOutState(outState: Bundle?) {
        this.outState = outState
    }

    override fun setIndex(currentIndex: Int) {
        this.currentIndex = currentIndex
    }

    override fun setEnterNumber(currentEnterNumber: String) {
        this.currentEnterNumber = currentEnterNumber
    }

    override fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    override fun getCurrentIndex(): Int = currentIndex

    override fun getCurrentEnterNumber(): String = currentEnterNumber

    override fun getErrorMessage(): String = errorMessage

    override fun saveState() {
        outState?.putInt(STATE_INT_INDEX, currentIndex)
        outState?.putString(STATE_STR_NUMBER, currentEnterNumber)
        outState?.putString(STATE_STR_ERROR, errorMessage)

        editState.putInt(STATE_INT_INDEX, currentIndex)
        editState.apply()
    }

    override fun setStateFromBundle(savedInstantState: Bundle) {
        setIndex(savedInstantState.getInt(STATE_INT_INDEX))
        setEnterNumber(savedInstantState.getString(STATE_STR_NUMBER) ?: "")
        setErrorMessage(savedInstantState.getString(STATE_STR_ERROR) ?: "")
    }

//    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
//    fun setEditState(newEditState: SharedPreferences.Editor) {
//        editState = newEditState
//    }

    private companion object {
        private const val STATE_INT_INDEX = "currentIndex"
        private const val STATE_STR_NUMBER = "currentEnterNumber"
        private const val STATE_STR_ERROR = "errorMessage"
    }
}