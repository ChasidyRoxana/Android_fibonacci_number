package com.example.fibonaccinumber

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

    fun findIndexOfTheNumber(newNumber: Int): Int {
        var newIndex = 0
        while (newIndex < listOfNumbers.lastIndex && newNumber > listOfNumbers[newIndex]) {
            newIndex++
        }
        if (newIndex > 0) {
            val closerToPrevious = newNumber - listOfNumbers[newIndex - 1]
            val closerToNext = listOfNumbers[newIndex] - newNumber
            if (closerToPrevious < closerToNext) {
                newIndex--
            }
        }
        return newIndex
    }

    fun changeIndexToNext() {
        if (currentIndex < listOfNumbers.lastIndex) {
            currentIndex++
        }
    }

    fun changeIndexToPrevious() {
        if (currentIndex > 0) {
            currentIndex--
        }
    }
}