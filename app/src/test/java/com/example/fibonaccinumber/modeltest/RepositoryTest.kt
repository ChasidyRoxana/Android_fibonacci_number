package com.example.fibonaccinumber.modeltest

import android.content.SharedPreferences
import android.os.Bundle
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
    fun setOutState_invokeWithBundle() {
        val bundleMock: Bundle = mock()

        repository.setOutState(bundleMock)

        assertEquals(bundleMock, repository.getOutState())
    }

    @Test
    fun setOutState_invokeWithNull() {
        repository.setOutState(null)

        assertNull(repository.getOutState())
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
    fun setErrorMessage_correct() {
        val error = "Error"

        repository.setErrorMessage(error)

        assertEquals(error, repository.getErrorMessage())
    }

    @Test
    fun setErrorType_correct() {
        val errorType = "CORRECT"

        repository.setErrorType(errorType)

        assertEquals(errorType, repository.getErrorType())
    }

    @Test
    fun saveState_BundleIsNull() {
        val outState: Bundle? = null
        repository.setOutState(outState)

        repository.saveState()

        verify(editStateMock).putInt(any(), any())
        verify(editStateMock).apply()
    }

    @Test
    fun saveState_BundleNotNull() {
        val outStateMock: Bundle = mock()
        repository.setOutState(outStateMock)

        repository.saveState()

        verify(outStateMock).putInt(any(), any())
        verify(outStateMock, times(3)).putString(any(), any())
        verify(editStateMock).putInt(any(), any())
        verify(editStateMock).apply()
    }

    @Test
    fun setStateFromBundle_correct() {
        val savedInstantStateMock: Bundle = mock()

        repository.setStateFromBundle(savedInstantStateMock)

        verify(savedInstantStateMock).getInt(any())
        verify(savedInstantStateMock, times(3)).getString(any())
    }

}