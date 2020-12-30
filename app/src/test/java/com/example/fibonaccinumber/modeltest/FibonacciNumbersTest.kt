package com.example.fibonaccinumber.modeltest

import com.example.fibonaccinumber.model.FibonacciNumbers
import org.junit.Test

import org.junit.Assert.*

class FibonacciNumbersTest {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

    @Test
    fun getNextNumber_returnNumber() {
        val index = fibonacciNumbers.findIndexOfTheNumber(5) // 0, 1, 1, 2, 3, 5, 8, 13
        fibonacciNumbers.setCurrentIndex(index)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertEquals(8, nextNumber)
    }

    @Test
    fun getNextNumber_returnNull() {
        fibonacciNumbers.setCurrentIndex(MAX_INDEX)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertNull(nextNumber)
    }

    @Test
    fun getPreviousNumber_returnNumber() {
        val index = fibonacciNumbers.findIndexOfTheNumber(5)
        fibonacciNumbers.setCurrentIndex(index)

        val previousNumber = fibonacciNumbers.getPreviousNumber()

        assertEquals(3, previousNumber)
    }

    @Test
    fun getPreviousNumber_returnNull() {
        fibonacciNumbers.setCurrentIndex(0)

        val previousNumber = fibonacciNumbers.getPreviousNumber()

        assertNull(previousNumber)
    }

    @Test
    fun setCurrentIndex_correct() {
        fibonacciNumbers.setCurrentIndex(10)

        fibonacciNumbers.setCurrentIndex(5)

        assertEquals(5, fibonacciNumbers.getCurrentIndex())
        assertNotEquals(10, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_negativeIndex() {
        fibonacciNumbers.setCurrentIndex(10)

        fibonacciNumbers.setCurrentIndex(-5)

        assertNotEquals(-5, fibonacciNumbers.getCurrentIndex())
        assertEquals(10, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_tooBigIndex() {
        fibonacciNumbers.setCurrentIndex(10)

        fibonacciNumbers.setCurrentIndex(500)

        assertNotEquals(500, fibonacciNumbers.getCurrentIndex())
        assertEquals(10, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun findIndexOfTheNumber_correct() {
        val index = fibonacciNumbers.findIndexOfTheNumber(8)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(8, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_closerToPrev() {
        val index = fibonacciNumbers.findIndexOfTheNumber(17) // between 13 and 21 (17 - 13 == 21 - 17)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(13, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_closerToNext() {
        val index = fibonacciNumbers.findIndexOfTheNumber(18) // between 13 and 21

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(21, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_negativeNumber() {
        val index = fibonacciNumbers.findIndexOfTheNumber(-5)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(0, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_tooBigNumber() {
        val index = fibonacciNumbers.findIndexOfTheNumber(Int.MAX_VALUE)

        fibonacciNumbers.setCurrentIndex(index)
        assertEquals(MAX_INDEX, index) // the number doesn't matter
    }

    @Test
    fun changeIndexToNext_correct() {
        fibonacciNumbers.setCurrentIndex(5)

        fibonacciNumbers.changeIndexToNext()

        assertEquals(6, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToNext_withLastIndex() {
        fibonacciNumbers.setCurrentIndex(MAX_INDEX)

        fibonacciNumbers.changeIndexToNext()

        val index = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(MAX_INDEX + 1, index)
        assertEquals(MAX_INDEX, index)
    }

    @Test
    fun changeToPrevious_correct() {
        fibonacciNumbers.setCurrentIndex(5)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(4, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeToPrevious_withFirstIndex() {
        fibonacciNumbers.setCurrentIndex(0)

        fibonacciNumbers.changeIndexToPrevious()

        val index = fibonacciNumbers.getCurrentIndex()
        assertNotEquals(-1, index)
        assertEquals(0, index)
    }

    @Test
    fun changeIndexToLast_correct() {
        fibonacciNumbers.setCurrentIndex(5)

        fibonacciNumbers.changeIndexToLast()

        assertEquals(MAX_INDEX, fibonacciNumbers.getCurrentIndex())
    }

    private companion object {
        private const val MAX_INDEX = 46 // Index = 0..46 (number[MAX_INDEX] < Int.MAX_VALUE)
    }
}