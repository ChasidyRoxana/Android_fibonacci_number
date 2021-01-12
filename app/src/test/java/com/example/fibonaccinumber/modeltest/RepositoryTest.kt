package com.example.fibonaccinumber.modeltest

import android.content.SharedPreferences
import com.example.fibonaccinumber.model.Repository
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private val sharedPreferencesMock: SharedPreferences = mock()
    private val editStateMock: SharedPreferences.Editor = mock()
    private val repository = Repository(sharedPreferencesMock)

    @Before
    fun setUp() {
        whenever(sharedPreferencesMock.edit()).thenReturn(editStateMock)
        whenever(sharedPreferencesMock.edit().putInt(any(), any())).thenReturn(editStateMock)
    }

    @Test
    fun saveState_correct_save_current_index() {
        val index = 10
        repository.currentIndex = index

        repository.saveState()

        verify(editStateMock).putInt(PREF_INT_INDEX, index)
        verify(editStateMock).apply()
    }

    private companion object {
        private const val PREF_INT_INDEX = "currentIndex"
    }
}