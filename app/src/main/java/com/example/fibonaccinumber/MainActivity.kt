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

    private fun initListeners() {
        imageButtonNext.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToNext()
            setTextViewNumbers()
        }

        imageButtonPrev.setOnClickListener {
            fibonacciNumbers.setCurrentIndexToPrevious()
            setTextViewNumbers()
        }

        imageButtonReset.setOnClickListener {
            fibonacciNumbers.setCurrentIndex(0)
            setTextViewNumbers()
        }
    }

    private fun setTextViewNumbers() {
        textViewCurrentNumber.text = fibonacciNumbers.getCurrentNumber().toString()

        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        textViewPreviousNumber.text = previousNumber?.toString()
        imageButtonPrev.isClickable = previousNumber != null

        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        textViewNextNumber.text = nextNumber?.toString()
        imageButtonNext.isClickable = nextNumber != null
    }
}