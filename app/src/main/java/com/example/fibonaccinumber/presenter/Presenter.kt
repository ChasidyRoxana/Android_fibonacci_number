package com.example.fibonaccinumber.presenter

import android.content.res.Resources
import androidx.annotation.VisibleForTesting
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.R
import com.example.fibonaccinumber.model.FibonacciNumbers
import java.lang.NumberFormatException

class Presenter(
    private val view: MainContract.MainView,
    private val repository: MainContract.MainRepository,
    private val resources: Resources,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
) : MainContract.MainPresenter {

    private var currentEnterNumber: String = ""
    private var messageType: MessageType = MessageType.CORRECT

    override fun onInitialized() {
        currentEnterNumber = repository.currentEnterNumber
        messageType = MessageType.valueOf(repository.messageType)
        val currentIndex = repository.currentIndex
        fibonacciNumbers.setCurrentIndex(currentIndex)
        changeCurrentState()
    }

    override fun onStop() {
        repository.currentIndex = fibonacciNumbers.getCurrentIndex()
        repository.currentEnterNumber = currentEnterNumber
        repository.messageType = messageType.name
        repository.saveState()
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
        messageType = MessageType.CORRECT
        changeCurrentState()
    }

    override fun onFindResultClicked() {
        messageType = try {
            val newNumber = currentEnterNumber.toInt()
            if (newNumberFound(newNumber)) {
                MessageType.CORRECT
            } else {
                MessageType.NOT_FOUND
            }
        } catch (e: NumberFormatException) {
            if (currentEnterNumber.isEmpty()) {
                fibonacciNumbers.setCurrentIndex(0)
                MessageType.EMPTY
            } else {
                fibonacciNumbers.changeIndexToLast()
                MessageType.WRONG
            }
        }
        changeCurrentState()
        view.clearEditText()
    }

    override fun textChanged(newString: String?) {
        currentEnterNumber = newString ?: ""
        changeFindButtonState()
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
        val errorMessage = getMessageForError()
        val colorId = getColorForError()
        view.setErrorMessageText(errorMessage)
        view.setErrorMessageColor(colorId)
    }

    private fun getMessageForError(): String {
        return when (messageType) {
            MessageType.CORRECT -> ""
            MessageType.EMPTY -> resources.getString(R.string.err_empty_string)
            MessageType.NOT_FOUND -> resources.getString(R.string.err_not_found_number)
            MessageType.WRONG -> resources.getString(R.string.err_wrong_number)
        }
    }

    private fun getColorForError(): Int =
        if (messageType == MessageType.WRONG || messageType == MessageType.EMPTY) {
            R.color.error_red
        } else {
            R.color.black
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
    fun getCurrentEnterNumber(): String = currentEnterNumber

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setCurrentEnterNumber(newEnterNumber: String) {
        currentEnterNumber = newEnterNumber
    }
}
