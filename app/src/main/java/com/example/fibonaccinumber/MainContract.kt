package com.example.fibonaccinumber

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
    }

    interface MainPresenter {
        fun imageButtonNextOnClick()
        fun imageButtonPrevOnClick()
        fun imageButtonResetOnClick()
        fun buttonFindResultOnClick(editTextNumber: String)
        fun saveState()
        fun loadState()
    }
}