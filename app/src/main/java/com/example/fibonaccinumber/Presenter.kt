package com.example.fibonaccinumber

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import java.lang.NumberFormatException

class Presenter(
    private val view: MainContract.MainView,
    private val sharedPreferences: SharedPreferences,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers(),
    private val repository: Repository = Repository(sharedPreferences)
) : MainContract.MainPresenter {

    private var currentTextNumber: String = ""
    private var errorMessage: String = ""

    override fun saveState(outState: Bundle) {
        repository.saveState(
            outState,
            fibonacciNumbers.getCurrentIndex(),
            currentTextNumber,
            errorMessage
        )
    }

    override fun loadState(savedInstantState: Bundle?) {
        if (savedInstantState != null) {
            repository.setState(savedInstantState)
        }
        currentTextNumber = repository.currentTextNumber
        errorMessage = repository.errorMessage
        val currentIndex = repository.currentIndex
        fibonacciNumbers.setCurrentIndex(currentIndex)
        changeCurrentState()
    }

    override fun onNextClicked() {
        fibonacciNumbers.changeIndexToNext()
        changeNumbers()
    }

    override fun onPrevClicked() {
        fibonacciNumbers.changeIndexToPrevious()
        changeNumbers()
    }

    override fun onResetClicked() {
        fibonacciNumbers.setCurrentIndex(0)
        errorMessage = ""
        currentTextNumber = ""
        changeCurrentState()
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
            if (currentTextNumber.isEmpty()) {
                view.getErrorEmptyString()
            } else {
                fibonacciNumbers.changeIndexToLast()
                view.getErrorWrongNumber()
            }
        }
        changeCurrentState()
    }

    override fun textChanged(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                saveTextNumber(s?.toString() ?: "")
                changeFindButtonState()
            }
        }
    }

    override fun imeAction(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onFindResultClicked()
            }
            false
        }
    }

    private fun saveTextNumber(editTextNumber: String) {
        currentTextNumber = editTextNumber
    }

    private fun changeFindButtonState() {
        val buttonOn: Boolean = currentTextNumber.isNotEmpty()
        view.toggleFindResult(buttonOn)
    }

    private fun newNumberFound(newNumber: Int): Boolean {
        val newIndex: Int = fibonacciNumbers.findIndexOfTheNumber(newNumber)
        fibonacciNumbers.setCurrentIndex(newIndex)
        return newNumber == fibonacciNumbers.getCurrentNumber()
    }

    private fun changeCurrentState() {
        changeNumbers()
        changeErrorMessage()
        changeFindButtonState()
        view.setTextNumber(currentTextNumber)
    }

    private fun changeErrorMessage() {
        val color =
            if (errorMessage == view.getErrorWrongNumber()) {
                view.getRedColor()
            } else {
                view.getBlackColor()
            }
        view.setErrorMessageText(errorMessage)
        view.setErrorMessageColor(color)
    }

    private fun changeNumbers() {
        changeCurrentNumber()
        changePreviousNumber()
        changeNextNumber()
    }

    private fun changeCurrentNumber() {
        val newNumber = fibonacciNumbers.getCurrentNumber().toString()
        view.setCurrentNumber(newNumber)
    }

    private fun changePreviousNumber() {
        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        val buttonState = previousNumber != null
        view.togglePrev(buttonState)
        view.setPreviousNumber(previousNumber?.toString() ?: "")
    }

    private fun changeNextNumber() {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val buttonState = nextNumber != null
        view.toggleNext(buttonState)
        view.setNextNumber(nextNumber?.toString() ?: "")
    }
}
