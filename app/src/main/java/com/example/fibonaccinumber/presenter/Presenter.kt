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

    private var enterNumber: String = ""
    private var messageType: MessageType = MessageType.CORRECT

    override fun onCreate() {
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

    override fun onFindResultClicked() {
        messageType = try {
            val number = enterNumber.toInt()
            getMessageTypeForGoodCase(number)
        } catch (e: NumberFormatException) {
            getMessageTypeForErrorCase()
        }
        updateState()
        view.clearEditTextAndCloseKeyboard()
    }

    override fun textChanged(enterNumber: String?) {
        this.enterNumber = enterNumber ?: ""
        updateFindButtonState()
    }

    private fun getMessageTypeForGoodCase(number: Int): MessageType {
        val index = fibonacciNumbers.findIndexOfTheNumber(number)
        fibonacciNumbers.setCurrentIndex(index)
        val typeOfError = if (number == fibonacciNumbers.getCurrentNumber()) {
            MessageType.CORRECT
        } else {
            MessageType.NOT_FOUND
        }
        return typeOfError
    }

    private fun getMessageTypeForErrorCase(): MessageType {
        val typeOfError: MessageType
        if (enterNumber.isEmpty()) {
            fibonacciNumbers.setCurrentIndex(0)
            typeOfError = MessageType.EMPTY
        } else {
            fibonacciNumbers.changeIndexToLast()
            typeOfError = MessageType.WRONG
        }
        return typeOfError
    }

    private fun updateState() {
        updateAllNumbers()
        updateErrorMessage()
        updateFindButtonState()
        view.setEnterNumber(enterNumber)
    }

    private fun updateFindButtonState() {
        val isEnabled: Boolean = enterNumber.isNotEmpty()
        view.setFindResultButtonEnabled(isEnabled)
    }

    private fun updateErrorMessage() {
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
            R.color.design_default_color_error
        } else {
            R.color.design_default_color_on_secondary
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
        view.setPrevButtonEnabled(isEnabled)
        view.setPreviousNumber(previousNumber?.toString() ?: "")
    }

    private fun changeNextNumber() {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val isEnabled = nextNumber != null
        view.setNextButtonEnabled(isEnabled)
        view.setNextNumber(nextNumber?.toString() ?: "")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getEnterNumber(): String =
        enterNumber

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setEnterNumber(enterNumber: String) {
        this.enterNumber = enterNumber
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getMessageType(): MessageType =
        messageType

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setMessageType(messageType: MessageType) {
        this.messageType = messageType
    }
}
