package com.example.fibonaccinumber.presentertest

import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.presenter.Presenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito

class PresenterTest {

    private val view: MainContract.MainView = mock()
    private val fibonacciNumbers: FibonacciNumbers = mock()
    private val repository: MainContract.MainRepository = mock()

//    private lateinit var view: MainContract.MainView
//    private lateinit var fibonacciNumbers: FibonacciNumbers
//    private lateinit var repository: Repository// = Mockito.spy(Repository(mock()))

//    private val presenter: Presenter = mock()
//    private val presenter = Presenter(view, repository, fibonacciNumbers)
    private lateinit var presenter: Presenter// = Presenter(view, repository, fibonacciNumbers)

    @Before
    fun setUp() {
////        val sharedPreferences: SharedPreferences = mock()
////        whenever(sharedPreferences.edit()).thenReturn(mock())
//        repository = mock() //Mockito.spy(Repository(sharedPreferences))
//        presenter = Presenter(view, repository, fibonacciNumbers)

        presenter = Presenter(view,repository,fibonacciNumbers)
    }

    @Test
    fun saveState_correct() {
//        repository.setEditState(mock())

        presenter.saveState(Mockito.any())

        verify(repository).setOutState(Mockito.any())
        verify(repository).setIndex(Mockito.anyInt())
        verify(repository).setEnterNumber(Mockito.anyString())
        verify(repository).setErrorMessage(Mockito.anyString())
        verify(repository).saveState()
    }

    @Test
    fun loadState() {
    }

    @Test
    fun onNextClicked_correct() {
        presenter.onNextClicked()

        Mockito.verify(fibonacciNumbers).changeIndexToNext()
    }

    @Test
    fun onPrevClicked_correct() {
        presenter.onPrevClicked()

        Mockito.verify(fibonacciNumbers).changeIndexToPrevious()
    }

    @Test
    fun onResetClicked_correct() {
        presenter.onResetClicked()

        assertEquals("", presenter.getCurrentEnterNumber())
        assertEquals("", presenter.getErrorMessage())
        assertEquals(0, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun onFindResultClicked_correct() {
        presenter.setCurrentEnterNumber("13")

        presenter.onFindResultClicked()

        assertEquals("", presenter.getErrorMessage())
        verify(view).clearEditText()
    }

    @Test
    fun onFindResultClicked_emptyStringError() {
        presenter.setCurrentEnterNumber("")

        presenter.onFindResultClicked()

        verify(view).getErrorEmptyString()
        verify(view).clearEditText()
    }

    @Test
    fun onFindResultClicked_notFoundError() {
        presenter.setCurrentEnterNumber("14")

        presenter.onFindResultClicked()

        verify(view).getErrorNotFound()
        verify(view).clearEditText()
    }

    @Test
    fun onFindResultClicked_wrongNumberError() {
        presenter.setCurrentEnterNumber(Int.MAX_VALUE.toString())

        presenter.onFindResultClicked()

        verify(view).getErrorWrongNumber()
        verify(view).clearEditText()
    }
}