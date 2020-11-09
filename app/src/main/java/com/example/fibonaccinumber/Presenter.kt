package com.example.fibonaccinumber

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class Presenter {

    val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
    var error: Boolean? = null
    var currentEditTextNumber: String = ""

    fun initListeners(mainActivity: MainActivity) {
        mainActivity.imageButtonNext.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToNext()
            mainActivity.setCurrentState()
        }

        mainActivity.imageButtonPrev.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToPrevious()
            mainActivity.setCurrentState()
        }

        mainActivity.imageButtonReset.setOnClickListener {
            fibonacciNumbers.setCurrentIndex(0)
            error = null
            currentEditTextNumber = ""
            mainActivity.setCurrentState()
        }

        mainActivity.buttonFindResult.setOnClickListener {
            currentEditTextNumber = mainActivity.editTextNumber.text.toString()
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
            mainActivity.setCurrentState()
        }
    }
}
