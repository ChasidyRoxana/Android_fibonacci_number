package com.example.fibonaccinumber

import android.content.SharedPreferences
import android.util.Log
import java.lang.NumberFormatException

const val CURRENT_INDEX = "CurrentIndex"
const val CURRENT_NUMBER = "CurrentEditTextNumber"

class Presenter(
    private val view: MainContract.MainView,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
) : MainContract.MainPresenter {

    private var sharedPreferences: SharedPreferences? = null
    private var currentTextNumber: String = ""
    private var errorMessage: String = ""

    override fun onNextClicked() {
        fibonacciNumbers.setCurrentIndexToNext()
        setNumbers()
    }

    override fun onPrevClicked() {
        fibonacciNumbers.setCurrentIndexToPrevious()
        setNumbers()
    }

    override fun onResetClicked() {
        fibonacciNumbers.setCurrentIndex(0)
        errorMessage = ""
        currentTextNumber = ""
        setCurrentState()
    }

    override fun onFindResultClicked(editTextNumber: String) {
        currentTextNumber = editTextNumber
        errorMessage = try {
            val newNumber = currentTextNumber.toInt()
            if (newNumberFound(newNumber)) {
                ""
            } else {
                view.getErrorNotFound()
            }
        } catch (e: NumberFormatException) {
            view.getErrorWrongNumber()
        }
        setCurrentState()
    }

    private fun newNumberFound(newNumber: Int): Boolean {
        val newIndex: Int = fibonacciNumbers.getIndexOfTheNumber(newNumber)
        fibonacciNumbers.setCurrentIndex(newIndex)
        return newNumber == fibonacciNumbers.getCurrentNumber()
    }

    override fun setSharedPref(shPref: SharedPreferences) {
        sharedPreferences = shPref
    }

    override fun saveState() {
        val editState = sharedPreferences?.edit()
        editState?.putInt(CURRENT_INDEX, fibonacciNumbers.getCurrentIndex())
        editState?.putString(CURRENT_NUMBER, currentTextNumber)
        editState?.apply()
    }

    override fun loadState() {
        val currentIndex = sharedPreferences?.getInt(CURRENT_INDEX, 0) ?: 0
        fibonacciNumbers.setCurrentIndex(currentIndex)

        val textNumber = sharedPreferences?.getString(CURRENT_NUMBER, "") ?: ""
        currentTextNumber = textNumber
//        Log.i("NAIDIERROR", "load data")
        setCurrentState()
//        Log.i("NAIDIERROR", "set data")
    }

    private fun setCurrentState() {
        setNumbers()
        setErrorMessage()
        view.setTextNumber(currentTextNumber)
    }

    private fun setErrorMessage() {
        view.setErrorMessageText(errorMessage)
        if (errorMessage == view.getErrorNotFound()) {
            val color = view.getColorNotFound()
            view.setErrorMessageColor(color)
        } else {
            val color = view.getColorWrongNumber()
            view.setErrorMessageColor(color)
        }
    }

    private fun setNumbers() {
        setCurrentNumber()
        setPreviousNumber()
        setNextNumber()
    }

    private fun setCurrentNumber() {
        val newNumber = fibonacciNumbers.getCurrentNumber().toString()
        view.setCurrentNumber(newNumber)
    }

    private fun setPreviousNumber() {
        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        val isClickable = previousNumber != null
        view.setPrevClickable(isClickable)
        view.setPreviousNumber(previousNumber?.toString() ?: "")
    }

    private fun setNextNumber() {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        val isClickable = nextNumber != null
        view.setNextClickable(isClickable)
        view.setNextNumber(nextNumber?.toString() ?: "")
    }
}
