package com.example.fibonaccinumber

import android.text.Editable

interface MainContract {
    interface MainView {
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun clearEditText()
        fun togglePrev(state: Boolean)
        fun toggleNext(state: Boolean)
        fun toggleFindResult(state: Boolean)
        fun setEnterNumber(newText: String)
        fun getErrorMessageById(messageId: Int):String
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(colorId: Int)
    }

    interface MainPresenter {
        fun saveState()
        fun loadState()
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked()
        fun textChanged(newString: Editable?)
    }

    interface MainRepository{
        fun setCurrentIndex(currentIndex: Int)
        fun setCurrentEnterNumber(currentEnterNumber: String)
        fun setErrorType(errorType: String)
        fun getCurrentIndex(): Int
        fun getCurrentEnterNumber(): String
        fun getErrorType(): String
        fun saveState()
    }
}