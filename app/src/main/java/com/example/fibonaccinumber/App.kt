package com.example.fibonaccinumber

import android.app.Application
import com.example.fibonaccinumber.model.Repository

class App : Application() {

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        repository = Repository(sharedPreferences)
    }

    private companion object {
        private const val PREF_FILE_NAME = "preferences"
    }
}