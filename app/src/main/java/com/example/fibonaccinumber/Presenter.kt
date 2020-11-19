package com.example.fibonaccinumber

import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
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

    override fun setFindButtonState() {
        val buttonOn: Boolean = currentTextNumber.isNotEmpty()
        view.toggleFindResult(buttonOn)
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
            fibonacciNumbers.changeIndexToLast()
            view.getErrorWrongNumber()
        }
        setCurrentState()
    }

    override fun textChanged(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                saveEditTextNumber(s?.toString() ?: "")
                setFindButtonState()
            }
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
        setFindButtonState()
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
