package com.example.fibonaccinumber

import androidx.annotation.ColorRes

interface MainContract {
    interface MainView {
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun clearEditTextAndCloseKeyboard()
        fun setPrevButtonEnabled(isEnabled: Boolean)
        fun setNextButtonEnabled(isEnabled: Boolean)
        fun setFindResultButtonEnabled(isEnabled: Boolean)
        fun setEnterNumber(text: String)
        fun setErrorMessageText(message: String)
        fun setErrorMessageColor(@ColorRes colorId: Int)
    }

    interface MainPresenter {
        fun onCreate()
        fun onStop()
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked()
        fun textChanged(enterNumber: String?)
    }
}