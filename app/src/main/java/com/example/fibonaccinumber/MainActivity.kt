package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

private val presenter: Presenter = Presenter()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentState()
        presenter.initListeners(this)
    }

    fun setCurrentState() {
        setTextViewNumbers()
        setTextViewErrorMessage()
        setEditTextNumber()
    }

    private fun setTextViewNumbers() {
        textViewCurrentNumber.text = presenter.fibonacciNumbers.getCurrentNumber().toString()

        val previousNumber: Int? = presenter.fibonacciNumbers.getPreviousNumber()
        textViewPreviousNumber.text = previousNumber?.toString()
        imageButtonPrev.isClickable = previousNumber != null

        val nextNumber: Int? = presenter.fibonacciNumbers.getNextNumber()
        textViewNextNumber.text = nextNumber?.toString()
        imageButtonNext.isClickable = nextNumber != null
    }

    private fun setTextViewErrorMessage() {
        textViewErrorMessage.text = when (presenter.error) {
            null -> {
                null
            }
            true -> {
                textViewErrorMessage.setTextColor(resources.getColor(R.color.errorRed, null))
                resources.getString(R.string.wrongNumber)
            }
            false -> {
                textViewErrorMessage.setTextColor(resources.getColor(R.color.black, null))
                resources.getString(R.string.notFoundNumber)
            }
        }
    }

    private fun setEditTextNumber() {
        val currentText = editTextNumber.text
        editTextNumber.text.replace(0, currentText.length, presenter.currentEditTextNumber)
    }
}