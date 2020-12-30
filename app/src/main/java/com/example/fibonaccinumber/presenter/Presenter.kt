package com.example.fibonaccinumber.presenter

import android.content.res.Resources
import androidx.annotation.VisibleForTesting
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.R
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.model.Repository
import java.lang.NumberFormatException

class Presenter(
    private val view: MainContract.MainView,
    private val resources: Resources,
    private val repository: Repository,
    private val fibonacciNumbers: FibonacciNumbers
) : MainContract.MainPresenter {

    private var enterNumber: String
    private var messageType: MessageType

    init {
        enterNumber = repository.enterNumber
        messageType = repository.messageType
        val currentIndex = repository.currentIndex
        fibonacciNumbers.setCurrentIndex(currentIndex)
        updateState()
    }

    override fun onStop() {
        repository.enterNumber = enterNumber
        repository.messageType = messageType
        repository.currentIndex = fibonacciNumbers.getCurrentIndex()
        repository.saveState()
    }

    override fun onNextClicked() {
        fibonacciNumbers.changeIndexToNext()
        updateAllNumbers()
    }

    override fun onPrevClicked() {
        fibonacciNumbers.changeIndexToPrevious()
        updateAllNumbers()
    }

    override fun onResetClicked() {
        fibonacciNumbers.setCurrentIndex(0)
        enterNumber = ""
        messageType = MessageType.CORRECT
        updateState()
    }

    override fun onFindResultClicked() { // fixme разбить это на функции и сделать читаемым
        try {
            val newNumber = enterNumber.toInt()
            val newIndex = fibonacciNumbers.findIndexOfTheNumber(newNumber)
            fibonacciNumbers.setCurrentIndex(newIndex)
            messageType = if (newNumber == fibonacciNumbers.getCurrentNumber()) {
                MessageType.CORRECT
            } else {
                MessageType.NOT_FOUND
            }
        } catch (e: NumberFormatException) {
            if (enterNumber.isEmpty()) {
                fibonacciNumbers.setCurrentIndex(0)
                messageType = MessageType.EMPTY
            } else {
                fibonacciNumbers.changeIndexToLast()
                messageType = MessageType.WRONG
            }
        }
        updateState()
        view.clearEditTextAndCloseKeyboard()
    }

    override fun textChanged(enterNumber: String?) {
        this.enterNumber = enterNumber ?: ""
        updateFindButtonState()
    }

    private fun updateFindButtonState() {
        val isEnabled: Boolean = enterNumber.isNotEmpty()
        view.setEnabledFindResultButton(isEnabled)
    }

    private fun updateState() {
        updateAllNumbers()
        changeErrorMessage()
        updateFindButtonState()
        view.setEnterNumber(enterNumber)
    }

    private fun changeErrorMessage() {
        val errorMessage = getErrorMessage()
        val colorId = getErrorColorId()
        view.setErrorMessageText(errorMessage)
        view.setErrorMessageColor(colorId)
    }

    private fun getErrorMessage(): String = when (messageType) {
        MessageType.CORRECT -> ""
        MessageType.EMPTY -> resources.getString(R.string.err_empty_string)
        MessageType.NOT_FOUND -> resources.getString(R.string.err_not_found_number)
        MessageType.WRONG -> resources.getString(R.string.err_wrong_number)
    }

    private fun getErrorColorId(): Int =
        if (messageType == MessageType.WRONG || messageType == MessageType.EMPTY) {
            R.color.error_red
        } else {
            R.color.black
        }

    private fun updateAllNumbers() {
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
        val isEnabled = previousNumber != null
        view.setEnabledPrevButton(isEnabled)
        view.setPreviousNumber(previousNumber?.toString() ?: "")
    }

    private fun changeNextNumber() {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val isEnabled = nextNumber != null
        view.setEnabledNextButton(isEnabled)
        view.setNextNumber(nextNumber?.toString() ?: "")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getEnterNumber(): String = enterNumber

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setEnterNumber(enterNumber: String) {
        this.enterNumber = enterNumber
    }
}
