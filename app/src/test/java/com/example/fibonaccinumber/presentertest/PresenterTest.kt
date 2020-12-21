package com.example.fibonaccinumber.presentertest

import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.model.FibonacciNumbers
import com.example.fibonaccinumber.model.Repository
import com.example.fibonaccinumber.presenter.Presenter
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify

import org.junit.Assert.*

class PresenterTest {

    private val view: MainContract.MainView = mock()
    private val repository: Repository = mock()
    private val fibonacciNumbers: FibonacciNumbers = mock()

    private val presenter: MainContract.MainPresenter =
        Presenter(view, repository, fibonacciNumbers)

    @Test
    fun saveState() {
    }

    @Test
    fun loadState() {
    }

    @Test
    fun onNextClicked() {
        presenter.onNextClicked()

        verify(fibonacciNumbers).changeIndexToNext()
        verify(view).setCurrentNumber(TEST_STR_NUMBER)
        verify(view).toggleNext(true)
        verify(view).setPreviousNumber(TEST_STR_NUMBER)
        verify(view).toggleNext(true)
        verify(view).setNextNumber(TEST_STR_NUMBER)
    }

    @Test
    fun onPrevClicked() {
        presenter.onPrevClicked()

        verify(fibonacciNumbers).changeIndexToPrevious()
        verify(view).setCurrentNumber(TEST_STR_NUMBER)
        verify(view).toggleNext(true)
        verify(view).setPreviousNumber(TEST_STR_NUMBER)
        verify(view).toggleNext(true)
        verify(view).setNextNumber(TEST_STR_NUMBER)
    }

    @Test
    fun onResetClicked() {
    }

    @Test
    fun onFindResultClicked() {
    }

    private companion object {
        private const val TEST_STR_NUMBER = "15"
    }
}