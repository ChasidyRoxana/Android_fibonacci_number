package com.example.fibonaccinumber

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
        fun setErrorMessageText(newMessage: String)
        fun setErrorMessageColor(newColor: Int)
    }

    interface MainPresenter {
        fun onInitialized()
        fun onSave()
        fun onNextClicked()
        fun onPrevClicked()
        fun onResetClicked()
        fun onFindResultClicked()
        fun textChanged(newString: String?)
    }

    interface MainRepository{
        var currentIndex: Int
        var enterNumber: String
        var messageType: String
        fun saveState()
    }
}