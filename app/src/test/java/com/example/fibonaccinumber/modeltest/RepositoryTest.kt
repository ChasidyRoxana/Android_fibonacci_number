package com.example.fibonaccinumber.modeltest

import android.content.SharedPreferences
import com.example.fibonaccinumber.model.Repository
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class RepositoryTest {

    @Test
    fun saveState_correct() {
        val sharedPreferencesMock: SharedPreferences = mock()
        val editStateMock: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesMock.edit()).thenReturn(editStateMock)
        val repository = Repository(sharedPreferencesMock)

        repository.saveState()

        verify(editStateMock).putInt(any(), any())
        verify(editStateMock).apply()
    }
}