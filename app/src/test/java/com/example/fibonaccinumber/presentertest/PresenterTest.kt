package com.example.fibonaccinumber.presentertest

import android.content.res.Resources
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.presenter.Presenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class PresenterTest {

    private val viewMock: MainContract.MainView = mock()
    private val fibonacciNumbersSpy: FibonacciNumbers = spy()
    private val repositoryMock: MainContract.MainRepository = mock()
    private val resources: Resources = mock()
    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        whenever(repositoryMock.messageType).thenReturn("CORRECT")
        whenever(repositoryMock.currentEnterNumber).thenReturn("")
        whenever(repositoryMock.currentIndex).thenReturn(5)
        whenever(resources.getString(any())).thenReturn("any message")

        presenter = Presenter(viewMock, repositoryMock, resources, fibonacciNumbersSpy)
    }

    @Test
    fun onStop_correct() {
        presenter.onStop()

        verify(repositoryMock).currentIndex = any()
        verify(repositoryMock).currentEnterNumber = any()
        verify(repositoryMock).messageType = any()
        verify(repositoryMock).saveState()
    }

    @Test
    fun onInitialized_correct() {
        presenter.onInitialized()

        verify(repositoryMock).currentIndex
        verify(repositoryMock).currentEnterNumber
        verify(repositoryMock).messageType
        verify(fibonacciNumbersSpy).setCurrentIndex(any())
    }

    @Test
    fun onNextClicked_correct() {
        presenter.onNextClicked()

        verify(fibonacciNumbersSpy).changeIndexToNext()
        verify(viewMock).setCurrentNumber(any())
        verify(viewMock).togglePrev(any())
        verify(viewMock).setPreviousNumber(any())
        verify(viewMock).toggleNext(any())
        verify(viewMock).setNextNumber(any())
    }

    @Test
    fun onPrevClicked_correct() {
        presenter.onPrevClicked()

        verify(fibonacciNumbersSpy).changeIndexToPrevious()
        verify(viewMock).setCurrentNumber(any())
        verify(viewMock).togglePrev(any())
        verify(viewMock).setPreviousNumber(any())
        verify(viewMock).toggleNext(any())
        verify(viewMock).setNextNumber(any())
    }

    @Test
    fun onResetClicked_correct() {
        presenter.onResetClicked()

        assertEquals("", presenter.getCurrentEnterNumber())
        assertEquals(0, fibonacciNumbersSpy.getCurrentIndex())
        verify(viewMock).setCurrentNumber("0")
        verify(viewMock).togglePrev(false)
        verify(viewMock).setPreviousNumber("")
        verify(viewMock).toggleNext(true)
        verify(viewMock).setNextNumber("1")
    }

    @Test
    fun onFindResultClicked_correct() {
        presenter.setCurrentEnterNumber("13")

        presenter.onFindResultClicked()

        verify(resources, never()).getString(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_notFoundError() {
        presenter.setCurrentEnterNumber("14")

        presenter.onFindResultClicked()

        verify(resources).getString(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_emptyStringError() {
        presenter.setCurrentEnterNumber("")

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).setCurrentIndex(0)
        verify(resources).getString(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_wrongNumberError() {
        presenter.setCurrentEnterNumber("2147483648") // Int.MAX_VALUE + 1

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).changeIndexToLast()
        verify(resources).getString(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun textChanged_nullString() {
        presenter.textChanged(null)

        verify(viewMock).toggleFindResult(any())
        assertEquals("", presenter.getCurrentEnterNumber())
    }

    @Test
    fun textChanged_notNullString() {
        val enterString = "156"

        presenter.textChanged(enterString)

        verify(viewMock).toggleFindResult(any())
        assertEquals(enterString, presenter.getCurrentEnterNumber())
    }
}