package com.example.fibonaccinumber.modeltest

import com.example.fibonaccinumber.model.FibonacciNumbers
import org.junit.Test

import org.junit.Assert.*

class FibonacciNumbersTest {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

    @Test
    fun getNextNumber_with_first_index_return_number() {
        val firstIndex = 0
        val expectedNumber = 1
        fibonacciNumbers.setCurrentIndex(firstIndex)

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
        val index = -5

        fibonacciNumbers.setCurrentIndex(index)

        assertNotEquals(index, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_with_too_big_index_not_set() {
        val index = 100

        fibonacciNumbers.setCurrentIndex(index)

        assertNotEquals(index, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun findIndexOfTheNumber_with_existing_number_return_its_index() {
        val number = 8

        val index = fibonacciNumbers.findIndexOfTheNumber(number)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(number, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_with_nonexistent_number_return_index_closer_to_prev() {
        val expectedNumber = 13
        val numberCloserTo13 = 17 // between 13 and 21 (17 - 13 == 21 - 17)

        val index = fibonacciNumbers.findIndexOfTheNumber(numberCloserTo13)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(expectedNumber, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_with_nonexistent_number_return_index_closer_to_next() {
        val expectedNumber = 21
        val numberCloserTo21 = 18

        val index = fibonacciNumbers.findIndexOfTheNumber(numberCloserTo21)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(expectedNumber, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_with_negative_number_return_first_index() {
        val expectedNumber = 0
        val negativeNumber = -5

        val index = fibonacciNumbers.findIndexOfTheNumber(negativeNumber)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(expectedNumber, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_with_too_big_number_return_last_index() {
        val index = fibonacciNumbers.findIndexOfTheNumber(Int.MAX_VALUE)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(LAST_INDEX, index) // the number doesn't matter
    }

    @Test
    fun changeIndexToNext_with_first_index_set_to_next() {
        val index = 0
        val expectedIndex = index + 1
        fibonacciNumbers.setCurrentIndex(index)

        fibonacciNumbers.changeIndexToNext()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToNext_with_valid_index_set_to_next() {
        val index = 15
        val expectedIndex = index + 1
        fibonacciNumbers.setCurrentIndex(index)

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
        val index = LAST_INDEX
        val expectedIndex = index - 1
        fibonacciNumbers.setCurrentIndex(index)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToPrevious_with_valid_index_set_to_prev() {
        val index = 15
        val expectedIndex = index - 1
        fibonacciNumbers.setCurrentIndex(index)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(expectedIndex, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToPrevious_with_first_index_not_set() {
        val index = 0
        val unexpectedIndex = index - 1
        fibonacciNumbers.setCurrentIndex(index)

        fibonacciNumbers.changeIndexToPrevious()

        val actualIndex = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(unexpectedIndex, actualIndex)
        assertEquals(index, actualIndex)
    }

    @Test
    fun changeIndexToLast_correct_set_index_to_last() {
        val anyIndex = 15
        fibonacciNumbers.setCurrentIndex(anyIndex)

        fibonacciNumbers.changeIndexToLast()

        assertEquals(LAST_INDEX, fibonacciNumbers.getCurrentIndex())
    }

    private companion object {
        private const val LAST_INDEX = 46 // Index = 0..46 (number[LAST_INDEX] < Int.MAX_VALUE)
    }
}