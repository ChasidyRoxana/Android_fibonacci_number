package com.example.fibonaccinumber.presentertest

import android.content.res.Resources
import androidx.annotation.ColorRes
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.R
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.model.Repository
import com.example.fibonaccinumber.presenter.MessageType
import com.example.fibonaccinumber.presenter.Presenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class PresenterTest {

    private val viewMock: MainContract.MainView = mock()
    private val fibonacciNumbersSpy: FibonacciNumbers = spy()
    private val repositoryMock: Repository = mock()
    private val resourcesMock: Resources = mock()
    private val presenter: Presenter = Presenter(
        viewMock, resourcesMock, repositoryMock, fibonacciNumbersSpy
    )

    @Before
    fun setUp() {
        whenever(resourcesMock.getString(R.string.err_empty_string)).thenReturn(ERR_STR_EMPTY)
        whenever(resourcesMock.getString(R.string.err_not_found_number))
            .thenReturn(ERR_STR_NOT_FOUND)
        whenever(resourcesMock.getString(R.string.err_wrong_number)).thenReturn(ERR_STR_WRONG)
    }

    private fun checkUpdateState(
        enterNumber: String,
        messageType: MessageType,
        currentNumber: String,
        prevNumber: String,
        nextNumber: String,
        prevEnabled: Boolean,
        nextEnabled: Boolean,
        errorMessage: String,
        @ColorRes errorColor: Int,
        findResultEnabled: Boolean,
    ) {
        checkUpdateNumbers(currentNumber, prevNumber, nextNumber, prevEnabled, nextEnabled)
        assertEquals(enterNumber, presenter.getEnterNumber())
        assertEquals(messageType, presenter.getMessageType())
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setFindResultButtonEnabled(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
    }

    private fun checkUpdateNumbers(
        currentNumber: String,
        prevNumber: String,
        nextNumber: String,
        prevEnabled: Boolean,
        nextEnabled: Boolean
    ) {
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setPrevButtonEnabled(prevEnabled)
        verify(viewMock).setNextButtonEnabled(nextEnabled)
    }

    @Test
    fun onCreate_when_launch_correct_update_state() {
        val enterNumber = ""
        val currentIndex = 5
        val messageType = MessageType.CORRECT
        val currentNumber = "5"
        val prevNumber = "3"
        val nextNumber = "8"
        val prevEnabled = true
        val nextEnabled = true
        val errorMessage = ""
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = false
        whenever(repositoryMock.enterNumber).thenReturn(enterNumber)
        whenever(repositoryMock.currentIndex).thenReturn(currentIndex)
        whenever(repositoryMock.messageType).thenReturn(messageType)

        presenter.onCreate()

        verify(fibonacciNumbersSpy).setCurrentIndex(currentIndex)
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onCreate_when_turning_correct_update_state() {
        val enterNumber = "14"
        val currentIndex = 7
        val messageType = MessageType.NOT_FOUND
        val currentNumber = "13"
        val prevNumber = "8"
        val nextNumber = "21"
        val prevEnabled = true
        val nextEnabled = true
        val errorMessage = ERR_STR_NOT_FOUND
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = true
        whenever(repositoryMock.enterNumber).thenReturn(enterNumber)
        whenever(repositoryMock.currentIndex).thenReturn(currentIndex)
        whenever(repositoryMock.messageType).thenReturn(messageType)

        presenter.onCreate()

        verify(fibonacciNumbersSpy).setCurrentIndex(currentIndex)
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onStop_correct_save_parameter() {
        val currentIndex = 10
        val enterNumber = "611"
        val messageType = MessageType.NOT_FOUND
        whenever(fibonacciNumbersSpy.getCurrentIndex()).thenReturn(currentIndex)
        presenter.setEnterNumber(enterNumber)
        presenter.setMessageType(messageType)

        presenter.onStop()

        verify(repositoryMock).currentIndex = currentIndex
        verify(repositoryMock).enterNumber = enterNumber
        verify(repositoryMock).messageType = messageType
        verify(repositoryMock).saveState()
    }

    @Test
    fun onNextClicked_with_first_index_correct_update_numbers() {
        val currentNumber = "1"
        val prevNumber = "0"
        val nextNumber = "1"
        val prevEnabled = true
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(0)

        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        checkUpdateNumbers(currentNumber, prevNumber, nextNumber, prevEnabled, nextEnabled)
    }

    @Test // на последнем индексе кнопка next неактивна
    fun onNextClicked_with_penultimate_index_correct_update_numbers() {
        val currentNumber = "1836311903"
        val prevNumber = "1134903170"
        val nextNumber = ""
        val prevEnabled = true
        val nextEnabled = false
        fibonacciNumbersSpy.setCurrentIndex(LAST_INDEX - 1)

        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        checkUpdateNumbers(currentNumber, prevNumber, nextNumber, prevEnabled, nextEnabled)
    }

    @Test // на первом индексе кнопка prev неактивна
    fun onPrevClicked_with_second_index_correct_update_numbers() {
        val currentNumber = "0"
        val prevNumber = ""
        val nextNumber = "1"
        val prevEnabled = false
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(1)

        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        checkUpdateNumbers(currentNumber, prevNumber, nextNumber, prevEnabled, nextEnabled)
    }

    @Test
    fun onPrevClicked_with_last_index_correct_update_numbers() {
        val currentNumber = "1134903170"
        val prevNumber = "701408733"
        val nextNumber = "1836311903"
        val prevEnabled = true
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(LAST_INDEX)

        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        checkUpdateNumbers(currentNumber, prevNumber, nextNumber, prevEnabled, nextEnabled)
    }

    @Test
    fun onResetClicked_correct_update_sate() {
        val enterNumber = ""
        val messageType = MessageType.CORRECT
        val currentIndex = 0
        val currentNumber = "0"
        val prevNumber = ""
        val nextNumber = "1"
        val prevEnabled = false
        val nextEnabled = true
        val errorMessage = ""
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = false

        presenter.onResetClicked()

        verify(fibonacciNumbersSpy).setCurrentIndex(currentIndex)
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onFindResultClicked_with_correctMessageType_correct_update_state() {
        val enterNumber = "21"
        presenter.setEnterNumber(enterNumber)
        val indexOfNumber = 8
        val messageType = MessageType.CORRECT
        val currentNumber = "21"
        val prevNumber = "13"
        val nextNumber = "34"
        val prevEnabled = true
        val nextEnabled = true
        val errorMessage = ""
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = true

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).findIndexOfTheNumber(enterNumber.toInt())
        verify(fibonacciNumbersSpy).setCurrentIndex(indexOfNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onFindResultClicked_with_notFoundMessageType_correct_update_state() {
        val enterNumber = Int.MAX_VALUE.toString()
        presenter.setEnterNumber(enterNumber)
        val indexOfNumber = LAST_INDEX
        val messageType = MessageType.NOT_FOUND
        val currentNumber = "1836311903"
        val prevNumber = "1134903170"
        val nextNumber = ""
        val prevEnabled = true
        val nextEnabled = false
        val errorMessage = ERR_STR_NOT_FOUND
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = true

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).findIndexOfTheNumber(enterNumber.toInt())
        verify(fibonacciNumbersSpy).setCurrentIndex(indexOfNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onFindResultClicked_with_emptyStringMessageType_correct_update_state() {
        val enterNumber = ""
        presenter.setEnterNumber(enterNumber)
        val indexOfNumber = 0
        val messageType = MessageType.EMPTY
        val currentNumber = "0"
        val prevNumber = ""
        val nextNumber = "1"
        val prevEnabled = false
        val nextEnabled = true
        val errorMessage = ERR_STR_EMPTY
        val errorColor = COLOR_RES_RED
        val findResultEnabled = false

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy, never()).findIndexOfTheNumber(any())
        verify(fibonacciNumbersSpy).setCurrentIndex(indexOfNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun onFindResultClicked_with_wrongNumberMessageType_correct_update_state() {
        val enterNumber = "2147483648" // Int.MAX_VALUE + 1
        presenter.setEnterNumber(enterNumber)
        val messageType = MessageType.WRONG
        val currentNumber = "1836311903"
        val prevNumber = "1134903170"
        val nextNumber = ""
        val prevEnabled = true
        val nextEnabled = false
        val errorMessage = ERR_STR_WRONG
        val errorColor = COLOR_RES_RED
        val findResultEnabled = true

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy, never()).findIndexOfTheNumber(any())
        verify(fibonacciNumbersSpy).changeIndexToLast()
        verify(viewMock).clearEditTextAndCloseKeyboard()
        checkUpdateState(
            enterNumber,
            messageType,
            currentNumber,
            prevNumber,
            nextNumber,
            prevEnabled,
            nextEnabled,
            errorMessage,
            errorColor,
            findResultEnabled
        )
    }

    @Test
    fun textChanged_with_null_findResultDisable_enterNumberEmpty() {
        val expectedEnterNumber = ""
        val expectedFindButtonEnableState = false

        presenter.textChanged(null)

        verify(viewMock).setFindResultButtonEnabled(expectedFindButtonEnableState)
        assertEquals(expectedEnterNumber, presenter.getEnterNumber())
    }

    @Test
    fun textChanged_with_nonNull_findResultEnable_enterNumberUpdate() {
        val enterString = "156"
        val expectedFindButtonEnableState = true

        presenter.textChanged(enterString)

        verify(viewMock).setFindResultButtonEnabled(expectedFindButtonEnableState)
        assertEquals(enterString, presenter.getEnterNumber())
    }

    private companion object {
        private const val LAST_INDEX = 46 // Index = 0..46 (number[LAST_INDEX] < Int.MAX_VALUE)

        private const val COLOR_RES_BLACK = R.color.design_default_color_on_secondary
        private const val COLOR_RES_RED = R.color.design_default_color_error

        private const val ERR_STR_EMPTY = "empty string message"
        private const val ERR_STR_NOT_FOUND = "not found number message"
        private const val ERR_STR_WRONG = "wrong number message"
    }
}