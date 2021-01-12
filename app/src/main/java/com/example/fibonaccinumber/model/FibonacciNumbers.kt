package com.example.fibonaccinumber.model

class FibonacciNumbers {

    private val listOfNumbers: ArrayList<Int> = arrayListOf(0, 1)
    private var currentIndex: Int = 0

    init {
        createListOfNumbers()
    }

    private fun createListOfNumbers() {
        var nextNumber: Int
        do {
            val lastNumber = listOfNumbers.last()
            val previousNumber = listOfNumbers[listOfNumbers.lastIndex - 1]
            nextNumber = lastNumber + previousNumber
            listOfNumbers.add(nextNumber)
            val previousNumberAfterAdd: Int = lastNumber
            val integerOverflow = (nextNumber + previousNumberAfterAdd) < 0
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

    fun setCurrentIndex(index: Int) {
        if (index in 0..listOfNumbers.lastIndex) {
            currentIndex = index
        }
    }

    fun findIndexOfTheNumber(number: Int): Int {
        var index = 0
        while (index < listOfNumbers.lastIndex && number > listOfNumbers[index]) {
            index++
        }
        if (index > 0) {
            val closerToPrevious = number - listOfNumbers[index - 1]
            val closerToNext = listOfNumbers[index] - number
            if (closerToPrevious <= closerToNext) {
                index--
            }
        }
        return index
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

    fun changeIndexToLast() {
        setCurrentIndex(listOfNumbers.lastIndex)
    }
}