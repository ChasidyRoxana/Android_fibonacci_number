package com.example.fibonaccinumber.presentertest

import android.os.Bundle
import android.text.Editable
import android.view.inputmethod.EditorInfo
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
        whenever(viewMock.getErrorEmptyString()).thenReturn(ERR_STR_EMPTY)
        whenever(viewMock.getErrorWrongNumber()).thenReturn(ERR_STR_WRONG)
        whenever(viewMock.getErrorNotFound()).thenReturn(ERR_STR_NOT_FOUND)

        whenever(repositoryMock.getErrorType()).thenReturn("CORRECT")
        whenever(repositoryMock.getErrorMessage()).thenReturn("")
        whenever(repositoryMock.getCurrentEnterNumber()).thenReturn("")
        whenever(repositoryMock.getCurrentIndex()).thenReturn(5)

        presenter = Presenter(viewMock, repositoryMock, fibonacciNumbersSpy)
    }

    @Test
    fun saveState_invokeWithNull() {
        presenter.saveState(null)

        verify(repositoryMock, never()).setOutState(null)
        verify(repositoryMock).setCurrentIndex(any())
        verify(repositoryMock).setCurrentEnterNumber(any())
        verify(repositoryMock).setErrorMessage(any())
        verify(repositoryMock).setErrorType(any())
        verify(repositoryMock).saveState()
    }

    @Test
    fun saveState_invokeWithBundle() {
        val bundleMock: Bundle = mock()

        presenter.saveState(bundleMock)

        verify(repositoryMock).setOutState(bundleMock)
        verify(repositoryMock).setCurrentIndex(any())
        verify(repositoryMock).setCurrentEnterNumber(any())
        verify(repositoryMock).setErrorMessage(any())
        verify(repositoryMock).setErrorType(any())
        verify(repositoryMock).saveState()
    }

    @Test
    fun loadState_firstLaunch_invokeWithNull() {
        presenter.loadState(null)

        verify(repositoryMock, never()).setStateFromBundle(any())
        verify(repositoryMock).getCurrentEnterNumber()
        verify(repositoryMock).getErrorMessage()
        verify(repositoryMock).getErrorType()
        verify(repositoryMock).getCurrentIndex()
        verify(fibonacciNumbersSpy).setCurrentIndex(any())
    }

    @Test
    fun loadState_restartApp_invokeWithBundle() {
        val bundleMock: Bundle = mock()

        presenter.loadState(bundleMock)

        verify(repositoryMock).setStateFromBundle(bundleMock)
        verify(repositoryMock).getCurrentEnterNumber()
        verify(repositoryMock).getErrorMessage()
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
        assertEquals("", presenter.getErrorMessage())
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

        assertEquals("", presenter.getErrorMessage())
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_emptyStringError() {
        presenter.setCurrentEnterNumber("")

        presenter.onFindResultClicked()

        assertEquals(ERR_STR_EMPTY, presenter.getErrorMessage())
        verify(viewMock).getErrorEmptyString()
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_notFoundError() {
        presenter.setCurrentEnterNumber("14")

        presenter.onFindResultClicked()

        assertEquals(ERR_STR_NOT_FOUND, presenter.getErrorMessage())
        verify(viewMock).getErrorNotFound()
        verify(viewMock).clearEditText()
    }

    @Test
    fun onFindResultClicked_wrongNumberError() {
        presenter.setCurrentEnterNumber("2147483648")

        presenter.onFindResultClicked()

        assertEquals(ERR_STR_WRONG, presenter.getErrorMessage())
        verify(viewMock).getErrorWrongNumber()
        verify(viewMock).clearEditText()
    }

    @Test
    fun textChanged_correct() {
        val textEditable: Editable? = null
        val textString = textEditable?.toString() ?: ""

        val textWatcher = presenter.textChanged()
        textWatcher.afterTextChanged(textEditable)

        verify(viewMock).toggleFindResult(any())
        assertEquals(textString, presenter.getCurrentEnterNumber())
    }

    @Test
    fun editorAction_searchButtonClicked() {
        val editorAction = presenter.editorAction()
        val returnValue = editorAction.onEditorAction(null, EditorInfo.IME_ACTION_SEARCH, null)

        assertEquals(true, returnValue)
        verify(viewMock).clearEditText()
    }

    @Test
    fun editorAction_otherButtonClicked() {
        val editorAction = presenter.editorAction()
        val returnValue = editorAction.onEditorAction(null, EditorInfo.IME_ACTION_DONE, null)

        assertEquals(false, returnValue)
        verify(viewMock, never()).clearEditText()
    }

    private companion object {
        private const val ERR_STR_EMPTY = "Error empty string"
        private const val ERR_STR_NOT_FOUND = "Error not found"
        private const val ERR_STR_WRONG = "Error wrong number"
    }
}