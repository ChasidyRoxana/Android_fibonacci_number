package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListeners()
    }

    private fun initListeners() {
        imageButtonNext.setOnClickListener {
            current_number.text = fibonacciNumbers.getNextNumber().toString()
        }

        imageButtonPrev.setOnClickListener {
            current_number.text = fibonacciNumbers.getPreviousNumber().toString()
        }
    }
}
