package com.example.fibonaccinumber

import android.util.Log

class FibonacciNumbers {

    private val listOfNumbers: ArrayList<Int> = arrayListOf(0, 1)
    private var currentIndex: Int = 0

    init {
        createListOfNumbers()
    }

    private fun createListOfNumbers() {
        var newNumber: Int
        do {
            val lastNumber = listOfNumbers.last()
            val previousNumber = listOfNumbers[listOfNumbers.lastIndex - 1]
            newNumber = lastNumber + previousNumber
            listOfNumbers.add(newNumber)
            val previousNumberAfterAdd: Int = lastNumber
            val integerOverflow = (newNumber + previousNumberAfterAdd) < 0
        } while (!integerOverflow)
//        Log.i("NAIDIERROR", "createListOfNumbers")
    }

    fun getCurrentNumber(): Int = listOfNumbers[currentIndex]

    fun getCurrentIndex(): Int = currentIndex

    fun getNextNumber(): Int? =
        if (currentIndex < listOfNumbers.lastIndex) {
            listOfNumbers[currentIndex + 1]
        } else {
            null
        }

    fun getPreviousNumber(): Int? =
        if (currentIndex > 0) {
            listOfNumbers[currentIndex - 1]
        } else {
            null
        }

    fun setCurrentIndex(newIndex: Int) {
        if (newIndex in 0..listOfNumbers.lastIndex) {
            currentIndex = newIndex
        }
    }

    fun getIndexOfTheNumber(newNumber: Int): Int {
        var newIndex = 0
        while (newIndex < listOfNumbers.lastIndex && newNumber > listOfNumbers[newIndex]) {
            newIndex++
        }
        return newIndex
    }

    fun setCurrentIndexToNext() {
        if (currentIndex < listOfNumbers.lastIndex) {
            currentIndex++
        }
    }

    fun setCurrentIndexToPrevious() {
        if (currentIndex > 0) {
            currentIndex--
        }
    }
}