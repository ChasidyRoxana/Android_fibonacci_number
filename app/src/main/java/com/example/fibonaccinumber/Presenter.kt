package com.example.fibonaccinumber

import android.content.SharedPreferences
import java.lang.NumberFormatException

const val CURRENT_INDEX = "CurrentIndex"
const val CURRENT_NUMBER = "CurrentEditTextNumber"

class Presenter(
    private val view: MainContract.MainView,
    private val sharedPreferences: SharedPreferences,
    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
) : MainContract.MainPresenter {

    private var error: Boolean? = false
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
        error = false
        currentEditTextNumber = ""
        setCurrentState()
    }

    override fun buttonFindResultOnClick(editTextNumber: String) {
        currentEditTextNumber = editTextNumber
        error = try {
            val newNumber = currentEditTextNumber.toInt()
            !correctNewNumber(newNumber)
        } catch (e: NumberFormatException) {
            null
        }
        setCurrentState()
    }

    private fun correctNewNumber(newNumber: Int): Boolean {
        val newIndex: Int = fibonacciNumbers.getIndexOfTheNumber(newNumber)
        fibonacciNumbers.setCurrentIndex(newIndex)
        return newNumber == fibonacciNumbers.getCurrentNumber()
    }

    override fun saveState() {
        val sharedPreferences = sharedPreferences
        val editState = sharedPreferences.edit()
        with(editState) {
            editState.putInt(CURRENT_INDEX, fibonacciNumbers.getCurrentIndex())
            putString(CURRENT_NUMBER, currentEditTextNumber)
            apply()
        }
    }

    override fun loadState() {
        val sharedPreferences = sharedPreferences
        val currentIndex = sharedPreferences.getInt(CURRENT_INDEX, 0)
        fibonacciNumbers.setCurrentIndex(currentIndex)

        val curEditTextNumber = sharedPreferences.getString(CURRENT_NUMBER, "")
        currentEditTextNumber = curEditTextNumber!!
        setCurrentState()
    }

    private fun setCurrentState() {
        setTextViewNumbers()
        setTextViewErrorMessage()
        view.setEditTextNumber(currentEditTextNumber)
    }

    private fun setTextViewErrorMessage() {
        when (error) {
            null -> view.setTVErrorMessageWrongNumber()
            true -> view.setTVErrorMessageNotFoundNumber()
            false -> view.setTVErrorMessageNull()
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
