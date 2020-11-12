package com.example.fibonaccinumber

import android.content.SharedPreferences

interface MainContract {
    interface MainView {
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun setPrevClickable(isClickable: Boolean)
        fun setNextClickable(isClickable: Boolean)
        fun setTextNumber(newText: String)
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(newColor: Int)
        fun getErrorNotFound(): String
        fun getErrorWrongNumber(): String
        fun getColorNotFound(): Int
        fun getColorWrongNumber(): Int
    }

    interface MainPresenter {
        fun setSharedPref(shPref: SharedPreferences)
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked(editTextNumber: String)
        fun saveState()
        fun loadState()
    }
}