package com.example.fibonaccinumber

import java.lang.NumberFormatException

class Presenter(private val view: MainContract.MainView): MainContract.MainPresenter {

    private val fibonacciNumbers: MainContract.MainModel = FibonacciNumbers(this)
    private var error: Boolean? = null
    private var currentEditTextNumber: String = ""

    override fun imageButtonNextOnClick() {
        fibonacciNumbers.setCurrentIndexToNext()
        setTextViewNumbers()
    }

    override fun imageButtonPrevOnClick() {
        fibonacciNumbers.setCurrentIndexToPrevious()
        setTextViewNumbers()
    }

    override fun imageButtonResetOnClick() {
        fibonacciNumbers.setCurrentIndex(0)
        error = null
        currentEditTextNumber = ""
        setCurrentState()
    }

    override fun buttonFindResultOnClick(editTextNumber: String) {
        currentEditTextNumber = editTextNumber
        error = try {
            val newNumber = currentEditTextNumber.toInt()
            with(fibonacciNumbers) {
                val newIndex: Int = getIndexOfTheNumber(newNumber)
                setCurrentIndex(newIndex)
            }
            if (newNumber == fibonacciNumbers.getCurrentNumber()) {
                null
            } else {
                false
            }
        } catch (e: NumberFormatException) {
            true
        }
        setCurrentState()
    }

    override fun saveState() {
        fibonacciNumbers.saveState(view, currentEditTextNumber)
    }

    override fun loadState() {
        fibonacciNumbers.loadState(view)
        setCurrentState()
    }

    override fun setCurrentEditTextNumber(newText: String) {
        currentEditTextNumber = newText
    }

    private fun setCurrentState() {
        setTextViewNumbers()
        setTextViewErrorMessage()
        view.setEditTextNumber(currentEditTextNumber)
    }

    private fun setTextViewErrorMessage() {
        when (error) {
            null -> {
                view.setTVErrorMessageNull()
            }
            true -> {
                view.setTVErrorMessageWrongNumber()
            }
            false -> {
                view.setTVErrorMessageNotFoundNumber()
            }
        }
    }

    private fun setTextViewNumbers() {
        view.setTextViewCurrentNumber(setCurrentNumber())

        view.setTextViewPreviousNumber(setPreviousNumber())

        view.setTextViewNextNumber(setNextNumber())
    }

    private fun setCurrentNumber(): Int =
        fibonacciNumbers.getCurrentNumber()

    private fun setPreviousNumber(): Int? {
        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        val isClickable = previousNumber != null
        view.setIBPrevClickable(isClickable)
        return previousNumber
    }

    private fun setNextNumber(): Int? {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val isClickable = nextNumber != null
        view.setIBNextClickable(isClickable)
        return nextNumber
    }
}
