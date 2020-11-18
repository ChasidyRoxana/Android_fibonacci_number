package com.example.fibonaccinumber

interface MainContract {
    interface MainView {
        fun getErrorNotFound(): String
        fun getErrorWrongNumber(): String
        fun getColorNotFound(): Int
        fun getColorWrongNumber(): Int
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun togglePrev(state: Boolean)
        fun toggleNext(state: Boolean)
        fun toggleFindResult(state: Boolean)
        fun setTextNumber(newText: String)
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(newColor: Int)
    }

    interface MainPresenter {
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun saveEditTextNumber(editTextNumber: String)
        fun onFindResultClicked()
        fun setButtonState(newText: CharSequence?)
        fun saveState()
        fun loadState()
    }
}