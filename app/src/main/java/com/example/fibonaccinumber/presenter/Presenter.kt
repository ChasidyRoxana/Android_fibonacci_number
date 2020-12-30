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

    private var enterNumber: String = ""
    private var messageType: MessageType = MessageType.CORRECT

    override fun onInitialized() {
        enterNumber = repository.enterNumber
        messageType = MessageType.valueOf(repository.messageType)
        val currentIndex = repository.currentIndex
        fibonacciNumbers.setCurrentIndex(currentIndex)
        changeCurrentState()
    }

    override fun onSave() {
        repository.enterNumber = enterNumber
        repository.messageType = messageType.name
        repository.currentIndex = fibonacciNumbers.getCurrentIndex()
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
        enterNumber = ""
        messageType = MessageType.CORRECT
        changeCurrentState()
    }

    override fun onFindResultClicked() {
        messageType = try {
            val newNumber = enterNumber.toInt()
            if (newNumberFound(newNumber)) {
                MessageType.CORRECT
            } else {
                MessageType.NOT_FOUND
            }
        } catch (e: NumberFormatException) {
            if (enterNumber.isEmpty()) {
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
        enterNumber = newString ?: ""
        changeFindButtonState()
    }

    private fun changeFindButtonState() {
        val buttonOn: Boolean = enterNumber.isNotEmpty()
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
        view.setEnterNumber(enterNumber)
    }

    private fun changeErrorMessage() {
        val errorMessage = getErrorMessage()
        val colorId = getErrorColor()
        view.setErrorMessageText(errorMessage)
        view.setErrorMessageColor(colorId)
    }

    private fun getErrorMessage(): String {
        return when (messageType) {
            MessageType.CORRECT -> ""
            MessageType.EMPTY -> resources.getString(R.string.err_empty_string)
            MessageType.NOT_FOUND -> resources.getString(R.string.err_not_found_number)
            MessageType.WRONG -> resources.getString(R.string.err_wrong_number)
        }
    }

    private fun getErrorColor(): Int =
        if (messageType == MessageType.WRONG || messageType == MessageType.EMPTY) {
            resources.getColor(R.color.error_red, null)
        } else {
            resources.getColor(R.color.black, null)
        }

    private fun changeNumbers() {
        changeCurrentNumber()
        changePreviousNumber()
        changeNextNumber()
    }

    private fun changeCurrentNumber() {
        val newNumber = fibonacciNumbers.getCurrentNumber()
        view.setCurrentNumber(newNumber.toString())
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
    fun getEnterNumber(): String = enterNumber

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setEnterNumber(enterNumber: String) {
        this.enterNumber = enterNumber
    }
}
