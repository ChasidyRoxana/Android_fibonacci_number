package com.example.fibonaccinumber

import android.content.SharedPreferences
import java.lang.NumberFormatException

class Presenter(
    private val view: MainContract.MainView,
    private val sharedPreferences: SharedPreferences,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers(),
    private val repository: Repository = Repository(sharedPreferences)
) : MainContract.MainPresenter {

    private var currentTextNumber: String = ""
    private var errorMessage: String = ""

    override fun saveState() {
        repository.saveState(fibonacciNumbers.getCurrentIndex(), currentTextNumber)
    }

    override fun loadState() {
        val currentIndex = repository.loadIndex()
        fibonacciNumbers.setCurrentIndex(currentIndex)
        currentTextNumber = repository.loadTextNumber()
        setCurrentState()
    }

    override fun onNextClicked() {
        fibonacciNumbers.changeIndexToNext()
        setNumbers()
    }

    override fun onPrevClicked() {
        fibonacciNumbers.changeIndexToPrevious()
        setNumbers()
    }

    override fun onResetClicked() {
        fibonacciNumbers.setCurrentIndex(0)
        errorMessage = ""
        currentTextNumber = ""
        setCurrentState()
    }

    override fun saveEditTextNumber(editTextNumber: String) {
        currentTextNumber = editTextNumber
    }

    override fun onFindResultClicked() {
        errorMessage = try {
            val newNumber = currentTextNumber.toInt()
            if (newNumberFound(newNumber)) {
                ""
            } else {
                view.getErrorNotFound()
            }
        } catch (e: NumberFormatException) {
//            if (currentTextNumber.isEmpty()) {
//                view.toggleFindResult(false)
//                "Nado perepisat' vzaimodeistvie with this string"
//            } else {
                view.getErrorWrongNumber()
//            }
        }
        setCurrentState()
        TODO("Nado podumat'")
    }

    override fun setButtonState(newText: CharSequence?) {
        if (newText != null) {
            val buttonOn: Boolean = newText.isNotEmpty()
            view.toggleFindResult(buttonOn)
        }
    }

    private fun newNumberFound(newNumber: Int): Boolean {
        val newIndex: Int = fibonacciNumbers.findIndexOfTheNumber(newNumber)
        fibonacciNumbers.setCurrentIndex(newIndex)
        return newNumber == fibonacciNumbers.getCurrentNumber()
    }

    private fun setCurrentState() {
        setNumbers()
        setErrorMessage()
        view.setTextNumber(currentTextNumber)
    }

    private fun setErrorMessage() {
        view.setErrorMessageText(errorMessage)
        if (errorMessage == view.getErrorNotFound()) {
            val color = view.getColorNotFound()
            view.setErrorMessageColor(color)
        } else {
            val color = view.getColorWrongNumber()
            view.setErrorMessageColor(color)
        }
    }

    private fun setNumbers() {
        setCurrentNumber()
        setPreviousNumber()
        setNextNumber()
    }

    private fun setCurrentNumber() {
        val newNumber = fibonacciNumbers.getCurrentNumber().toString()
        view.setCurrentNumber(newNumber)
    }

    private fun setPreviousNumber() {
        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        val isClickable = previousNumber != null
        view.togglePrev(isClickable)
        view.setPreviousNumber(previousNumber?.toString() ?: "")
    }

    private fun setNextNumber() {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val isClickable = nextNumber != null
        view.toggleNext(isClickable)
        view.setNextNumber(nextNumber?.toString() ?: "")
    }
}
