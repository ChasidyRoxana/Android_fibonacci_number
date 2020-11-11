package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private val sharedPreferences = getPreferences(MODE_PRIVATE)
    private val presenter: MainContract.MainPresenter = Presenter(this, sharedPreferences)

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
            presenter.buttonFindResultOnClick(editTextNumber.text.toString())
        }
    }

    override fun onPause() {
        presenter.saveState()
        super.onPause()
    }

    override fun setTextViewCurrentNumber(number: Int) {
        textViewCurrentNumber.text = number.toString()
    }

    override fun setTextViewPreviousNumber(number: Int?) {
        textViewPreviousNumber.text = number?.toString()
    }

    override fun setIBPrevClickable(isClickable: Boolean) {
        imageButtonPrev.isClickable = isClickable
    }

    override fun setTextViewNextNumber(number: Int?) {
        textViewNextNumber.text = number?.toString()
    }

    override fun setIBNextClickable(isClickable: Boolean) {
        imageButtonNext.isClickable = isClickable
    }

    override fun setEditTextNumber(str: String) {
        val currentText = editTextNumber.text
        currentText.replace(0, currentText.length, str)
    }

    override fun setTVErrorMessageNull() {
        textViewErrorMessage.text = null
    }

    override fun setTVErrorMessageWrongNumber() {
        val color = resources.getColor(R.color.errorRed, null)
        textViewErrorMessage.setTextColor(color)
        textViewErrorMessage.text = resources.getString(R.string.wrongNumber)
    }

    override fun setTVErrorMessageNotFoundNumber() {
        val color = resources.getColor(R.color.black, null)
        textViewErrorMessage.setTextColor(color)
        textViewErrorMessage.text = resources.getString(R.string.notFoundNumber)
    }
}