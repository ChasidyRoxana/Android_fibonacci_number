package com.example.fibonaccinumber.model

import android.content.SharedPreferences
import com.example.fibonaccinumber.presenter.MessageType

class Repository(private val sharedPreferences: SharedPreferences) {

    var currentIndex: Int = sharedPreferences.getInt(PREF_INT_INDEX, 0)
    var messageType: MessageType = MessageType.CORRECT
    var enterNumber: String = ""

    fun saveState() {
        sharedPreferences.edit()
            .putInt(PREF_INT_INDEX, currentIndex)
            .apply()
    }

    private companion object {
        private const val PREF_INT_INDEX = "currentIndex"
    }
}