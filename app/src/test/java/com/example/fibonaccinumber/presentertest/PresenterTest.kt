package com.example.fibonaccinumber.presentertest

import android.content.res.Resources
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
        val enterNumber = ""
        val currentIndex = 5
        val messageType = MessageType.CORRECT

        whenever(repositoryMock.enterNumber).thenReturn(enterNumber)
        whenever(repositoryMock.currentIndex).thenReturn(currentIndex)
        whenever(repositoryMock.messageType).thenReturn(messageType)
        whenever(resourcesMock.getString(R.string.err_empty_string)).thenReturn(ERR_STR_EMPTY)
        whenever(resourcesMock.getString(R.string.err_not_found_number)).thenReturn(
            ERR_STR_NOT_FOUND
        )
        whenever(resourcesMock.getString(R.string.err_wrong_number)).thenReturn(ERR_STR_WRONG)
    }

    @Test
    fun onCreate_when_launch() {
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

        assertEquals(enterNumber, presenter.getEnterNumber())
        assertEquals(messageType, presenter.getMessageType())
        verify(fibonacciNumbersSpy).setCurrentIndex(currentIndex)
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
    }

    @Test
    fun onCreate_when_turning() {
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

        assertEquals(enterNumber, presenter.getEnterNumber())
        assertEquals(messageType, presenter.getMessageType())
        verify(fibonacciNumbersSpy).setCurrentIndex(currentIndex)
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
    }

    @Test
    fun onStop_correct() {
        val currentIndex = 10
        val enterNumber = "611"
        val messageType = MessageType.NOT_FOUND
        fibonacciNumbersSpy.setCurrentIndex(currentIndex)
        presenter.setEnterNumber(enterNumber)
        presenter.setMessageType(messageType)

        presenter.onStop()

        verify(repositoryMock).currentIndex = currentIndex
        verify(repositoryMock).enterNumber = enterNumber
        verify(repositoryMock).messageType = messageType
        verify(repositoryMock).saveState()
    }

    @Test
    fun onNextClicked_with_first_index() {
        val currentNumber = "1"
        val prevNumber = "0"
        val nextNumber = "1"
        val prevEnabled = true
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(0)

        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
    }

    @Test
    fun onNextClicked_with_penultimate_index() { // на последнем индексе кнопка неактивна
        val currentNumber = "1836311903"
        val prevNumber = "1134903170"
        val nextNumber = ""
        val prevEnabled = true
        val nextEnabled = false
        fibonacciNumbersSpy.setCurrentIndex(LAST_INDEX - 1)

        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
    }

    @Test
    fun onPrevClicked_with_second_index() { // на первом индексе кнопка неактивна
        val currentNumber = "0"
        val prevNumber = ""
        val nextNumber = "1"
        val prevEnabled = false
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(1)

        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
    }

    @Test
    fun onPrevClicked_with_last_index() {
        val currentNumber = "1134903170"
        val prevNumber = "701408733"
        val nextNumber = "1836311903"
        val prevEnabled = true
        val nextEnabled = true
        fibonacciNumbersSpy.setCurrentIndex(LAST_INDEX)

        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
    }

    @Test
    fun onResetClicked_correct_work() {
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

        assertEquals(enterNumber, presenter.getEnterNumber())
        assertEquals(messageType, presenter.getMessageType())
        assertEquals(currentIndex, fibonacciNumbersSpy.getCurrentIndex())
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
    }

    @Test
    fun onFindResultClicked_with_correct_message_type() {
        val enterNumber = "21"
        presenter.setEnterNumber(enterNumber)
        val expectedMessageType = MessageType.CORRECT
        val indexOfNumber = 8
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
        assertEquals(expectedMessageType, presenter.getMessageType())
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_with_not_found_error_message() {
        val enterNumber = "14"
        presenter.setEnterNumber(enterNumber)
        val expectedMessageType = MessageType.NOT_FOUND
        val indexOfNumber = 7
        val currentNumber = "13"
        val prevNumber = "8"
        val nextNumber = "21"
        val prevEnabled = true
        val nextEnabled = true
        val errorMessage = ERR_STR_NOT_FOUND
        val errorColor = COLOR_RES_BLACK
        val findResultEnabled = true

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).findIndexOfTheNumber(enterNumber.toInt())
        verify(fibonacciNumbersSpy).setCurrentIndex(indexOfNumber)
        assertEquals(expectedMessageType, presenter.getMessageType())
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_with_empty_string_error_message() {
        val enterNumber = ""
        presenter.setEnterNumber(enterNumber)
        val expectedMessageType = MessageType.EMPTY
        val indexOfNumber = 0
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
        assertEquals(expectedMessageType, presenter.getMessageType())
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_with_wrong_number_error_message() {
        val enterNumber = "2147483648" // Int.MAX_VALUE + 1
        presenter.setEnterNumber(enterNumber)
        val expectedMessageType = MessageType.WRONG
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
        assertEquals(expectedMessageType, presenter.getMessageType())
        verify(viewMock).setCurrentNumber(currentNumber)
        verify(viewMock).setPreviousNumber(prevNumber)
        verify(viewMock).setNextNumber(nextNumber)
        verify(viewMock).setEnabledPrevButton(prevEnabled)
        verify(viewMock).setEnabledNextButton(nextEnabled)
        verify(viewMock).setErrorMessageText(errorMessage)
        verify(viewMock).setErrorMessageColor(errorColor)
        verify(viewMock).setEnabledFindResultButton(findResultEnabled)
        verify(viewMock).setEnterNumber(enterNumber)
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun textChanged_with_null_string() {
        val expectedEnterNumber = ""
        val expectedFindButtonEnableState = false

        presenter.textChanged(null)

        verify(viewMock).setEnabledFindResultButton(expectedFindButtonEnableState)
        assertEquals(expectedEnterNumber, presenter.getEnterNumber())
    }

    @Test
    fun textChanged_with_not_null_string() {
        val enterString = "156"
        val expectedFindButtonEnableState = true

        presenter.textChanged(enterString)

        verify(viewMock).setEnabledFindResultButton(expectedFindButtonEnableState)
        assertEquals(enterString, presenter.getEnterNumber())
    }

    private companion object {
        private const val LAST_INDEX = 46 // Index = 0..46 (number[LAST_INDEX] < Int.MAX_VALUE)

        private const val COLOR_RES_BLACK = R.color.black
        private const val COLOR_RES_RED = R.color.error_red

        private const val ERR_STR_EMPTY = "empty string message"
        private const val ERR_STR_NOT_FOUND = "not found number message"
        private const val ERR_STR_WRONG = "wrong number message"
    }
}