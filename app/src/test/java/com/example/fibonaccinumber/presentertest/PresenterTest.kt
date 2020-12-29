package com.example.fibonaccinumber.presentertest

import android.text.Editable
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
    private lateinit var presenter: Presenter

    @Before
    fun setUp() {
        whenever(repositoryMock.getErrorType()).thenReturn("CORRECT")
        whenever(repositoryMock.getCurrentEnterNumber()).thenReturn("")
        whenever(repositoryMock.getCurrentIndex()).thenReturn(5)

        presenter = Presenter(viewMock, repositoryMock, fibonacciNumbersSpy)
    }

    @Test
    fun saveState_correct() {
        presenter.saveState()

        verify(repositoryMock).setCurrentIndex(any())
        verify(repositoryMock).setCurrentEnterNumber(any())
        verify(repositoryMock).setErrorType(any())
        verify(repositoryMock).saveState()
    }

    @Test
    fun loadState_correct() {
        presenter.loadState()

        verify(repositoryMock).getCurrentEnterNumber()
        verify(repositoryMock).getErrorType()
        verify(repositoryMock).getCurrentIndex()
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

    @Test // тесты для onFindResultClicked() одинаковые, но обрабатываются разные ошибки
    fun onFindResultClicked_correct() {
        presenter.setCurrentEnterNumber("13")

        presenter.onFindResultClicked()

        verify(viewMock).getErrorMessageById(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_notFoundError() {
        presenter.setCurrentEnterNumber("14")

        presenter.onFindResultClicked()

        verify(viewMock).getErrorMessageById(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_emptyStringError() {
        presenter.setCurrentEnterNumber("")

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).setCurrentIndex(0)
        verify(viewMock).getErrorMessageById(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_wrongNumberError() {
        presenter.setCurrentEnterNumber("2147483648") // Int.MAX_VALUE + 1

        presenter.onFindResultClicked()

        verify(fibonacciNumbersSpy).changeIndexToLast()
        verify(viewMock).getErrorMessageById(any())
        verify(viewMock).clearEditText()
    }

    @Test
    fun textChanged_nullString() {
        val textEditable: Editable? = null // как сделать Editable ?
        val textString = textEditable?.toString() ?: ""

        presenter.textChanged(textEditable)

        verify(viewMock).toggleFindResult(any())
        assertEquals(textString, presenter.getCurrentEnterNumber())
    }

    // fun textChanged_notNullString()
}