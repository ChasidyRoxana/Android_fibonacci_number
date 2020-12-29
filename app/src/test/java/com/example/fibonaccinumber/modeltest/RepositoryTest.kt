package com.example.fibonaccinumber.modeltest

import android.content.SharedPreferences
import com.example.fibonaccinumber.model.Repository
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RepositoryTest {

    private lateinit var repository: Repository
    private val editStateMock: SharedPreferences.Editor = mock()

    @Before
    fun setUp() {
        val sharedPreferencesMock: SharedPreferences = mock()
        whenever(sharedPreferencesMock.edit()).thenReturn(editStateMock)

        repository = Repository(sharedPreferencesMock)
    }

    @Test
    fun setCurrentIndex_correct() {
        val number = 5

        repository.setCurrentIndex(number)

        assertEquals(number, repository.getCurrentIndex())
    }

    @Test
    fun setCurrentEnterNumber_correct() {
        val enterNumber = "15"

        repository.setCurrentEnterNumber(enterNumber)

        assertEquals(enterNumber, repository.getCurrentEnterNumber())
    }

    @Test
    fun setErrorType_correct() {
        val errorType = "CORRECT"

        repository.setErrorType(errorType)

        assertEquals(errorType, repository.getErrorType())
    }

    @Test
    fun saveState_correct() {
        repository.saveState()

        verify(editStateMock).putInt(any(), any())
        verify(editStateMock).apply()
    }
}