package com.example.fibonaccinumber.modeltest

import com.example.fibonaccinumber.model.FibonacciNumbers
import org.junit.Test

import org.junit.Assert.*

class FibonacciNumbersTest {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

    @Test
    fun getNextNumber_with_first_index_return_number() {
        val expectedNumber = 1
        fibonacciNumbers.setCurrentIndex(0)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertEquals(expectedNumber, nextNumber)
    }

    @Test
    fun getNextNumber_with_valid_index_return_number() {
        val indexForNumber8 = 6
        val expectedNumber = 13
        fibonacciNumbers.setCurrentIndex(indexForNumber8)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertEquals(expectedNumber, nextNumber)
    }

    @Test
    fun getNextNumber_with_last_index_return_null() {
        fibonacciNumbers.setCurrentIndex(LAST_INDEX)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertNull(nextNumber)
    }

    @Test
    fun getPreviousNumber_with_valid_index_return_number() {
        val indexForNumber21 = 8
        val expectedNumber = 13
        fibonacciNumbers.setCurrentIndex(indexForNumber21)

        val previousNumber = fibonacciNumbers.getPreviousNumber()

        assertEquals(expectedNumber, previousNumber)
    }

    @Test
    fun getPreviousNumber_with_last_index_return_number() {
        val expectedNumber = 1134903170
        fibonacciNumbers.setCurrentIndex(LAST_INDEX)

        val previousNumber = fibonacciNumbers.getPreviousNumber()

        assertEquals(expectedNumber, previousNumber)
    }

    @Test
    fun getPreviousNumber_with_first_index_return_null() {
        fibonacciNumbers.setCurrentIndex(0)

        val previousNumber = fibonacciNumbers.getPreviousNumber()

        assertNull(previousNumber)
    }

    @Test
    fun setCurrentIndex_with_first_index_correct_set() {
        val index = 0

        fibonacciNumbers.setCurrentIndex(index)

        assertEquals(index, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_with_valid_index_correct_set() {
        val index = 15

        fibonacciNumbers.setCurrentIndex(index)

        assertEquals(index, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_with_last_index_correct_set() {
        val index = LAST_INDEX

        fibonacciNumbers.setCurrentIndex(index)

        assertEquals(index, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_with_negative_index_not_set() {
        val indexBeforeSet = 3
        val negativeIndex = -5
        fibonacciNumbers.setCurrentIndex(indexBeforeSet)

        fibonacciNumbers.setCurrentIndex(negativeIndex)

        val actualIndex = fibonacciNumbers.getCurrentIndex()
        assertEquals(indexBeforeSet, actualIndex)
        assertNotEquals(negativeIndex, actualIndex)
    }

    @Test
    fun setCurrentIndex_with_too_big_index_not_set() {
        val indexBeforeTooBigIndex = 5
        val tooBidIndex = Int.MAX_VALUE
        fibonacciNumbers.setCurrentIndex(indexBeforeTooBigIndex)

        fibonacciNumbers.setCurrentIndex(tooBidIndex)

        val index = fibonacciNumbers.getCurrentIndex()
        assertEquals(indexBeforeTooBigIndex, index)
        assertNotEquals(tooBidIndex, index)
    }

    @Test
    fun findIndexOfTheNumber_with_existing_number_return_its_index() {
        val number = 8
        val expectedIndexOfNumber8 = 6

        val index = fibonacciNumbers.findIndexOfTheNumber(number)

        assertEquals(expectedIndexOfNumber8, index)
    }

    @Test
    fun findIndexOfTheNumber_with_nonexistent_number_return_index_closer_to_prev() {
        val numberCloserTo13 = 17
        val expectedIndexOfNumber13 = 7

        val index = fibonacciNumbers.findIndexOfTheNumber(numberCloserTo13)

        assertEquals(expectedIndexOfNumber13, index)
    }

    @Test
    fun findIndexOfTheNumber_with_nonexistent_number_return_index_closer_to_next() {
        val numberCloserTo21 = 18
        val expectedIndexOfNumber21 = 8

        val index = fibonacciNumbers.findIndexOfTheNumber(numberCloserTo21)

        assertEquals(expectedIndexOfNumber21, index)
    }

    @Test
    fun findIndexOfTheNumber_with_negative_number_return_first_index() {
        val negativeNumber = -5
        val expectedIndex = 0

        val index = fibonacciNumbers.findIndexOfTheNumber(negativeNumber)

        assertEquals(expectedIndex, index)
    }

    @Test
    fun findIndexOfTheNumber_with_too_big_number_return_last_index() {
        val index = fibonacciNumbers.findIndexOfTheNumber(Int.MAX_VALUE)

        assertEquals(LAST_INDEX, index)
    }

    @Test
    fun changeIndexToNext_with_first_index_set_to_next() {
        val firstIndex = 0
        val expectedIndex = firstIndex + 1
        fibonacciNumbers.setCurrentIndex(firstIndex)

        fibonacciNumbers.changeIndexToNext()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToNext_with_valid_index_set_to_next() {
        val validIndex = 15
        val expectedIndex = validIndex + 1
        fibonacciNumbers.setCurrentIndex(validIndex)

        fibonacciNumbers.changeIndexToNext()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToNext_with_last_index_not_set() {
        fibonacciNumbers.setCurrentIndex(LAST_INDEX)

        fibonacciNumbers.changeIndexToNext()

        val actualIndex = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(LAST_INDEX + 1, actualIndex)
        assertEquals(LAST_INDEX, actualIndex)
    }

    @Test
    fun changeIndexToPrevious_with_last_index_set_to_prev() {
        val expectedIndex = LAST_INDEX - 1
        fibonacciNumbers.setCurrentIndex(LAST_INDEX)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToPrevious_with_valid_index_set_to_prev() {
        val validIndex = 15
        val expectedIndex = validIndex - 1
        fibonacciNumbers.setCurrentIndex(validIndex)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToPrevious_with_first_index_not_set() {
        val firstIndex = 0
        val unexpectedIndex = firstIndex - 1
        fibonacciNumbers.setCurrentIndex(firstIndex)

        fibonacciNumbers.changeIndexToPrevious()

        val actualIndex = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(unexpectedIndex, actualIndex)
        assertEquals(firstIndex, actualIndex)
    }

    @Test
    fun changeIndexToLast_correct_set_index_to_last() {
        val indexBeforeChange = 15
        fibonacciNumbers.setCurrentIndex(indexBeforeChange)

        fibonacciNumbers.changeIndexToLast()

        val actualIndex = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(indexBeforeChange, actualIndex)
        assertEquals(LAST_INDEX, actualIndex)
    }

    private companion object {
        private const val LAST_INDEX = 46 // number[LAST_INDEX] < Int.MAX_VALUE
    }
}