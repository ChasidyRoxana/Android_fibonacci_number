package com.example.fibonaccinumber.modeltest

import com.example.fibonaccinumber.model.FibonacciNumbers
import org.junit.Test

import org.junit.Assert.*

class FibonacciNumbersTest {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

    @Test
    fun getNextNumber_returnNumber() {
        val index = fibonacciNumbers.findIndexOfTheNumber(5)
        fibonacciNumbers.setCurrentIndex(index)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertEquals(8, nextNumber)
    }

    @Test
    fun getNextNumber_returnNull() {
        fibonacciNumbers.setCurrentIndex(MAX_INDEX)

        val nextNumber = fibonacciNumbers.getNextNumber()

        assertEquals(null, nextNumber)
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

        assertEquals(null, previousNumber)
    }

    @Test
    fun setCurrentIndex_correct() {
        fibonacciNumbers.setCurrentIndex(5)

        assertEquals(5, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_negativeIndex() {
        fibonacciNumbers.setCurrentIndex(-5)

        assertNotEquals(-5, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun setCurrentIndex_tooBigIndex() {
        fibonacciNumbers.setCurrentIndex(500)

        assertNotEquals(500, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun findIndexOfTheNumber_correct() {
        val index = fibonacciNumbers.findIndexOfTheNumber(8)
        fibonacciNumbers.setCurrentIndex(index)

        assertEquals(8, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_withChoicePrev() {
        val index = fibonacciNumbers.findIndexOfTheNumber(17) // between 13 and 21
        fibonacciNumbers.setCurrentIndex(index)

        assertEquals(13, fibonacciNumbers.getCurrentNumber())
    }

    @Test
    fun findIndexOfTheNumber_withChoiceNext() {
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
    fun changeIndexToNext_lastIndex() {
        fibonacciNumbers.setCurrentIndex(MAX_INDEX)

        fibonacciNumbers.changeIndexToNext()

        assertNotEquals(MAX_INDEX + 1, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeToPrevious_correct() {
        fibonacciNumbers.setCurrentIndex(5)

        fibonacciNumbers.changeIndexToPrevious()

        assertEquals(4, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeToPrevious_firstIndex() {
        fibonacciNumbers.setCurrentIndex(0)

        fibonacciNumbers.changeIndexToPrevious()

        assertNotEquals(-1, fibonacciNumbers.getCurrentIndex())
    }

    @Test
    fun changeIndexToLast_correct() {
        fibonacciNumbers.changeIndexToLast()

        assertEquals(MAX_INDEX, fibonacciNumbers.getCurrentIndex())
    }

    private companion object {
        private const val MAX_INDEX = 46 // Index == 0..46(number[index] < Int.MAX_VALUE)
    }
}