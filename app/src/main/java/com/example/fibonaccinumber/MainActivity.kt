package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()
        .apply { createListOfNumbers() }

    fun onClickPreviousNumber(view: View) {
        val number: TextView = findViewById(R.id.current_number)
        number.setText(fibonacciNumbers.getPreviousNumber().toString())
    }

    fun onClickNextNumber(view: View) {
        val number: TextView = findViewById(R.id.current_number)
        number.setText(fibonacciNumbers.getNextNumber().toString())
    }
}

