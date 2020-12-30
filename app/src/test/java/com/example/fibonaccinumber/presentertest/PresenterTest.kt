package com.example.fibonaccinumber.presentertest

import android.content.res.Resources
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.model.Repository
import com.example.fibonaccinumber.presenter.Presenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class PresenterTest {

    private val viewMock: MainContract.MainView = mock()
    private val fibonacciNumbersSpy: FibonacciNumbers = spy()
    private val repositoryMock: Repository = mock()
    private val resources: Resources = mock()
    private val presenter: Presenter = Presenter(viewMock, resources, repositoryMock, fibonacciNumbersSpy)

    @Before
    fun setUp() {
//        whenever(repositoryMock.messageType).thenReturn("CORRECT")
        whenever(repositoryMock.enterNumber).thenReturn("")
        whenever(repositoryMock.currentIndex).thenReturn(5)
        whenever(resources.getString(any())).thenReturn("any message")

    }

    @Test
    fun onStop_correct() {
        presenter.onStop()

        verify(repositoryMock).currentIndex = any()
        verify(repositoryMock).enterNumber = any()
        verify(repositoryMock).messageType = any()
        verify(repositoryMock).saveState()
    }

    @Test
    fun onNextClicked_correct() {
        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        verify(viewMock).setCurrentNumber(any())
        verify(viewMock).setEnabledPrevButton(any())
        verify(viewMock).setPreviousNumber(any())
        verify(viewMock).setEnabledNextButton(any())
        verify(viewMock).setNextNumber(any())
    }

    @Test
    fun onPrevClicked_correct() {
        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        verify(viewMock).setCurrentNumber(any())
        verify(viewMock).setEnabledPrevButton(any())
        verify(viewMock).setPreviousNumber(any())
        verify(viewMock).setEnabledNextButton(any())
        verify(viewMock).setNextNumber(any())
    }

    @Test
    fun onResetClicked_correct() {
        presenter.onResetClicked()

        assertEquals("", presenter.getEnterNumber())
        assertEquals(0, fibonacciNumbersSpy.getCurrentIndex())
        verify(viewMock).setCurrentNumber("0")
        verify(viewMock).setEnabledPrevButton(false)
        verify(viewMock).setPreviousNumber("")
        verify(viewMock).setEnabledNextButton(true)
        verify(viewMock).setNextNumber("1")
    }

    @Test
    fun onFindResultClicked_correct() {
        presenter.setEnterNumber("13")

        presenter.onFindResultClicked()

        verify(resources, never()).getString(any())
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_notFoundError() {
        presenter.setEnterNumber("14")

        presenter.onFindResultClicked()

        verify(resources).getString(any())
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_emptyStringError() {
        presenter.setEnterNumber("")

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).setCurrentIndex(0)
        verify(resources).getString(any())
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun onFindResultClicked_wrongNumberError() {
        presenter.setEnterNumber("2147483648") // Int.MAX_VALUE + 1

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).changeIndexToLast()
        verify(resources).getString(any())
        verify(viewMock).clearEditTextAndCloseKeyboard()
    }

    @Test
    fun textChanged_nullString() {
        presenter.textChanged(null)

        verify(viewMock).setEnabledFindResultButton(any())
        assertEquals("", presenter.getEnterNumber())
    }

    @Test
    fun textChanged_notNullString() {
        val enterString = "156"

        presenter.textChanged(enterString)

        verify(viewMock).setEnabledFindResultButton(any())
        assertEquals(enterString, presenter.getEnterNumber())
    }
}