package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val presenter: Presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.loadState()
        initListeners()
    }

    private fun initListeners() {
        imageButtonNext.setOnClickListener {
            presenter.imageButtonNextOnClick()
        }

        imageButtonPrev.setOnClickListener {
            presenter.imageButtonPrevOnClick()
        }

        imageButtonReset.setOnClickListener {
            presenter.imageButtonResetOnClick()
        }

        buttonFindResult.setOnClickListener {
            presenter.buttonFindResultOnClick()
        }
    }

    override fun onPause() {
        presenter.saveState()
        super.onPause()
    }

    fun setTextViewCurrentNumber(number: Int) {
        textViewCurrentNumber.text = number.toString()
    }

    fun setTextViewPreviousNumber(number: Int?) {
        textViewPreviousNumber.text = number?.toString()
    }

    fun setTextViewNextNumber(number: Int?) {
        textViewNextNumber.text = number?.toString()
    }

    fun setEditTextNumber(str: String) {
        val currentText = editTextNumber.text
        currentText.replace(0, currentText.length, str)
    }

    fun setTextViewErrorMessage(str: String?) {
        textViewErrorMessage.text = str
    }
}