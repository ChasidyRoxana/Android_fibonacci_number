package com.example.fibonaccinumber

import android.content.SharedPreferences

interface MainContract {
    interface MainView {
        fun setTextViewCurrentNumber(number: Int)
        fun setTextViewPreviousNumber(number: Int?)
        fun setIBPrevClickable(isClickable: Boolean)
        fun setTextViewNextNumber(number: Int?)
        fun setIBNextClickable(isClickable: Boolean)
        fun setEditTextNumber(str: String)
        fun setTVErrorMessageNull()
        fun setTVErrorMessageWrongNumber()
        fun setTVErrorMessageNotFoundNumber()
        fun getMyPreferences(): SharedPreferences
    }

    interface MainPresenter {
        fun imageButtonNextOnClick()
        fun imageButtonPrevOnClick()
        fun imageButtonResetOnClick()
        fun buttonFindResultOnClick(editTextNumber: String)
        fun saveState()
        fun loadState()
        fun setCurrentEditTextNumber(newText: String)
    }

    interface MainModel {
        fun saveState(view: MainView, currentEditTextNumber: String)
        fun loadState(view: MainView)
        fun getCurrentNumber(): Int
        fun getNextNumber(): Int?
        fun getPreviousNumber(): Int?
        fun setCurrentIndex(newIndex: Int)
        fun getIndexOfTheNumber(newNumber: Int): Int
        fun setCurrentIndexToNext()
        fun setCurrentIndexToPrevious()
    }
}