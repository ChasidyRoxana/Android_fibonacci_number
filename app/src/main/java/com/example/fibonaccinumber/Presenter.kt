package com.example.fibonaccinumber

import android.content.Context.MODE_PRIVATE
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class Presenter(private val view: MainActivity) {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
    private var error: Boolean? = null
    private var currentEditTextNumber: String = ""

    fun imageButtonNextOnClick() {
        fibonacciNumbers.setCurrentIndexToNext()
        setTextViewNumbers()
    }

    fun imageButtonPrevOnClick() {
        fibonacciNumbers.setCurrentIndexToPrevious()
        setTextViewNumbers()
    }

    fun imageButtonResetOnClick() {
        fibonacciNumbers.setCurrentIndex(0)
        error = null
        currentEditTextNumber = ""
        setCurrentState()
    }

    fun buttonFindResultOnClick() {
        currentEditTextNumber = view.editTextNumber.text.toString()
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

    fun saveState() {
        val sharedPreferences = view.getPreferences(MODE_PRIVATE)
        val editState = sharedPreferences.edit()
        with(editState) {
            editState.putInt("CurrentIndex", fibonacciNumbers.getCurrentIndex())
            putString("CurrentEditTextNumber", currentEditTextNumber)
            apply()
        }
    }

    fun loadState() {
        val sharedPreferences = view.getPreferences(MODE_PRIVATE)
        val currentIndex = sharedPreferences.getInt("CurrentIndex", 0)
        fibonacciNumbers.setCurrentIndex(currentIndex)
        val curEditTextNumber = sharedPreferences.getString("CurrentEditTextNumber", "")
        currentEditTextNumber = curEditTextNumber!!
        setCurrentState()
    }

    private fun setCurrentState() {
        setTextViewNumbers()
        setTextViewErrorMessage()
        view.setEditTextNumber(currentEditTextNumber)
    }

    private fun setTextViewErrorMessage() {
        val errorMessage = when (error) {
            null -> {
                null
            }
            true -> {
                val color = view.resources.getColor(R.color.errorRed, null)
                view.textViewErrorMessage.setTextColor(color)
                view.resources.getString(R.string.wrongNumber)
            }
            false -> {
                val color = view.resources.getColor(R.color.black, null)
                view.textViewErrorMessage.setTextColor(color)
                view.resources.getString(R.string.notFoundNumber)
            }
        }
        view.setTextViewErrorMessage(errorMessage)
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
        view.imageButtonPrev.isClickable = previousNumber != null
        return previousNumber
    }

    private fun setNextNumber(): Int? {
        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        view.imageButtonNext.isClickable = nextNumber != null
        return nextNumber
    }
}
