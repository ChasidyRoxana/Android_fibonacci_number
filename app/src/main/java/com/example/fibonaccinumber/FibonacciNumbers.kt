package com.example.fibonaccinumber

class FibonacciNumbers {

	private val listOfNumbers: ArrayList<Int> = arrayListOf(0, 1)
	private var currentIndex: Int = 0

	init {
		createListOfNumbers()
	}

	private fun createListOfNumbers() {
		var number: Int
		do {
			val lastNumber = listOfNumbers.last()
			val previousNumber = listOfNumbers[listOfNumbers.lastIndex - 1]
			number = lastNumber + previousNumber
			listOfNumbers.add(number)
			val integerOverflow = (number + listOfNumbers[listOfNumbers.lastIndex - 1]) < 0
		} while (!integerOverflow)
	}

	fun getCurrentNumber(): Int = listOfNumbers[currentIndex]

	fun getNextNumber(): Int? =
		if (currentIndex + 1 <= listOfNumbers.lastIndex) {
			listOfNumbers[currentIndex + 1]
		}
		else {
			null
		}

	fun getPreviousNumber(): Int? =
		if (currentIndex - 1 >= 0) {
			listOfNumbers[currentIndex - 1]
		}
		else {
			null
		}

	fun setCurrentIndex(newIndex: Int) {
		if (newIndex in 0..listOfNumbers.lastIndex)
			currentIndex = newIndex
	}

    fun getIndexOfTheNumber(newNumber: Int): Int {
        var newIndex = 0
        while (newIndex < listOfNumbers.lastIndex
			&& newNumber > listOfNumbers[newIndex])
            newIndex++
        return newIndex
    }

	fun setCurrentIndexToNext() {
		if (currentIndex < listOfNumbers.lastIndex)
			currentIndex++
	}

	fun setCurrentIndexToPrevious() {
		if (currentIndex > 0)
			currentIndex--
	}
}