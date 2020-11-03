package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

private val fibonacciNumbers: FibonacciNumbers = FibonacciNumbers()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTextViewNumbers()
        initListeners()
    }

    private fun setTextViewNumbers() {
        textViewPreviousNumber.text = fibonacciNumbers.getPreviousNumber()?.toString() ?: " "
        textViewCurrentNumber.text = fibonacciNumbers.getCurrentNumber().toString()
        textVieNextNumber.text = fibonacciNumbers.getNextNumber()?.toString() ?: " "
    }

    private fun initListeners() {
        imageButtonNext.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToNext()
            setTextViewNumbers()
        }

        imageButtonPrev.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToPrevious()
            setTextViewNumbers()
        }
    }
}
