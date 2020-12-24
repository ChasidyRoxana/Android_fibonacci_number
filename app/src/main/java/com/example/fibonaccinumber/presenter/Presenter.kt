package com.example.fibonaccinumber.presenter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.model.FibonacciNumbers
import java.lang.NumberFormatException

class Presenter(
    private val view: MainContract.MainView,
    private val repository: MainContract.MainRepository,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
) : MainContract.MainPresenter {

    private var currentEnterNumber: String = ""
    private var errorMessage: String = ""
    private var errorType: ErrorType = ErrorType.CORRECT

    override fun saveState(outState: Bundle?) {
        if (outState != null) {
            repository.setOutState(outState)
        }
        repository.setCurrentIndex(fibonacciNumbers.getCurrentIndex())
        repository.setCurrentEnterNumber(currentEnterNumber)
        repository.setErrorMessage(errorMessage)
        repository.setErrorType(errorType.name)
        repository.saveState()
    }

    override fun loadState(savedInstantState: Bundle?) {
        if (savedInstantState != null) {
            repository.setStateFromBundle(savedInstantState)
        }
        currentEnterNumber = repository.getCurrentEnterNumber()
        errorMessage = repository.getErrorMessage()
        errorType = ErrorType.valueOf(repository.getErrorType())
        val currentIndex = repository.getCurrentIndex()
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
        currentEnterNumber = ""
        errorMessage = ""
        errorType = ErrorType.CORRECT
        changeCurrentState()
    }

    override fun onFindResultClicked() {
        errorType = try {
            val newNumber = currentEnterNumber.toInt()
            if (newNumberFound(newNumber)) {
                ErrorType.CORRECT
            } else {
                ErrorType.NOT_FOUND
            }
        } catch (e: NumberFormatException) {
            if (currentEnterNumber.isEmpty()) {
                ErrorType.EMPTY
            } else {
                fibonacciNumbers.changeIndexToLast()
                ErrorType.WRONG
            }
        }
        changeCurrentState()
        view.clearEditText()
    }

    override fun textChanged(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                currentEnterNumber = s?.toString() ?: ""
                changeFindButtonState()
            }
        }
    }

    override fun editorAction(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onFindResultClicked()
                true
            } else {
                false
            }
        }
    }

    private fun changeFindButtonState() {
        val buttonOn: Boolean = currentEnterNumber.isNotEmpty()
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
        view.setEnterNumber(currentEnterNumber)
    }

    private fun changeErrorMessage() {
        errorMessage = getMessageForError()
        val color = getColorForError()
        view.setErrorMessageText(errorMessage)
        view.setErrorMessageColor(color)
    }

    private fun getMessageForError(): String = when (errorType) {
        ErrorType.CORRECT -> ""
        ErrorType.EMPTY -> view.getErrorEmptyString()
        ErrorType.NOT_FOUND -> view.getErrorNotFound()
        ErrorType.WRONG -> view.getErrorWrongNumber()
    }

    private fun getColorForError(): Int =
        if (errorType == ErrorType.WRONG || errorType == ErrorType.EMPTY) {
            view.getRedColor()
        } else {
            view.getBlackColor()
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

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getErrorMessage(): String = errorMessage

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getCurrentEnterNumber(): String = currentEnterNumber

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setCurrentEnterNumber(newEnterNumber: String) {
        currentEnterNumber = newEnterNumber
    }
}
