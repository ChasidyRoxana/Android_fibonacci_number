package com.example.fibonaccinumber

import android.os.Bundle
import android.text.TextWatcher
import android.widget.TextView

interface MainContract {
    interface MainView {
        fun getErrorNotFound(): String
        fun getErrorWrongNumber(): String
        fun getErrorEmptyString(): String
        fun getBlackColor(): Int
        fun getRedColor(): Int
        fun setCurrentNumber(number: String)
        fun setPreviousNumber(number: String)
        fun setNextNumber(number: String)
        fun clearEditText()
        fun togglePrev(state: Boolean)
        fun toggleNext(state: Boolean)
        fun toggleFindResult(state: Boolean)
        fun setEnterNumber(newText: String)
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(newColor: Int)
    }

    interface MainPresenter {
        fun saveState(outState: Bundle)
        fun loadState(savedInstantState: Bundle?)
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked()
        fun textChanged(): TextWatcher
        fun editorAction(): TextView.OnEditorActionListener
    }
}