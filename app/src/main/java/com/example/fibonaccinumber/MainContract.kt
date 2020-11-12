package com.example.fibonaccinumber

interface MainContract {
    interface MainView {
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun setPrevClickable(isClickable: Boolean)
        fun setNextClickable(isClickable: Boolean)
        fun setFindResultClickable(isClickable: Boolean)
        fun setPrevEnabled(isEnabled: Boolean)
        fun setNextEnabled(isEnabled: Boolean)
        fun setFindResultEnabled(isEnabled: Boolean)
        fun setTextNumber(newText: String)
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(newColor: Int)
        fun getErrorNotFound(): String
        fun getErrorWrongNumber(): String
        fun getColorNotFound(): Int
        fun getColorWrongNumber(): Int
    }

    interface MainPresenter {
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked(editTextNumber: String)
        fun saveState()
        fun loadState()
    }
}