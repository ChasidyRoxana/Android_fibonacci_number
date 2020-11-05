package com.example.fibonaccinumber

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

fun initListeners(mainActivity: MainActivity, fibonacciNumbers: FibonacciNumbers) {
    mainActivity.imageButtonNext.setOnClickListener {
        fibonacciNumbers.setCurrentIndexToNext()
        mainActivity.setTextViewNumbers()
    }

    mainActivity.imageButtonPrev.setOnClickListener {
        fibonacciNumbers.setCurrentIndexToPrevious()
        mainActivity.setTextViewNumbers()
    }

    mainActivity.imageButtonReset.setOnClickListener {
        fibonacciNumbers.setCurrentIndex(0)
        mainActivity.apply {
            setTextViewNumbers()
            setTextViewErrorMessage(false)
            mainActivity.editTextNumber.text = null
            TODO()
        }
    }

    mainActivity.buttonFindResult.setOnClickListener {
        val enteredString = mainActivity.editTextNumber.text.toString()
        mainActivity.editTextNumber.text = null
        try {
            val newNumber = enteredString.toInt()
            fibonacciNumbers.apply {
                val newIndex: Int = getIndexOfTheNumber(newNumber)
                setCurrentIndex(newIndex)
            }
            mainActivity.apply {
                setTextViewNumbers()
                if (newNumber == fibonacciNumbers.getCurrentNumber()){
                    setTextViewErrorMessage(null)
                } else {
                    setTextViewErrorMessage(false)
                }
            }
        } catch (e: NumberFormatException) {
            mainActivity.setTextViewErrorMessage(true)
        }
        TODO()
    }
}
