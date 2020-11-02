package com.example.fibonaccinumber

class FibonacciNumbers {

	private val listOfNumbers: ArrayList<Int> = arrayListOf(0, 1)
	private var currentIndex: Int = 0

	fun createListOfNumbers() {
		var number: Int
		do {
			number = listOfNumbers[listOfNumbers.size - 1] + listOfNumbers[listOfNumbers.size - 2]
			listOfNumbers.add(number)
		} while (number + listOfNumbers[listOfNumbers.size - 2] > 0)
	}

	fun getNextNumber(): Int =
			if (currentIndex < listOfNumbers.size - 1) listOfNumbers[++currentIndex]
			else listOfNumbers[currentIndex]

	fun getPreviousNumber(): Int =
			if (currentIndex > 0) listOfNumbers[--currentIndex]
			else listOfNumbers[currentIndex]
}