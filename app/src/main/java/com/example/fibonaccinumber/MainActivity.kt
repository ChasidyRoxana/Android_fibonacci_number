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
        initListeners(this, fibonacciNumbers)
    }

    fun setTextViewNumbers() {
        textViewCurrentNumber.text = fibonacciNumbers.getCurrentNumber().toString()

        val previousNumber: Int? = fibonacciNumbers.getPreviousNumber()
        textViewPreviousNumber.text = previousNumber?.toString()
        imageButtonPrev.isClickable = previousNumber != null

        val nextNumber: Int? = fibonacciNumbers.getNextNumber()
        textViewNextNumber.text = nextNumber?.toString()
        imageButtonNext.isClickable = nextNumber != null
    }

    fun setTextViewErrorMessage(error: Boolean?) {
        when (error) {
            null -> {
                textViewErrorMessage.text = null
            }
            true -> {
                textViewErrorMessage.apply {
                    text = resources.getString(R.string.wrongNumber)
                    setTextColor(resources.getColor(R.color.red))
                }
            }
            false -> {
                textViewErrorMessage.apply {
                    text = resources.getString(R.string.notFoundNumber)
                    setTextColor(resources.getColor(R.color.black))
                }
            }
        }
        TODO()
    }
}