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
		} while (number + listOfNumbers[listOfNumbers.lastIndex - 1] > 0)
	}

	fun getNextNumber(): Int {
		if (currentIndex < listOfNumbers.lastIndex)
			currentIndex++
		return listOfNumbers[currentIndex]
	}

	fun getPreviousNumber(): Int {
		if (currentIndex > 0)
			currentIndex--
		return listOfNumbers[currentIndex]
	}
}