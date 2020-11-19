package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private lateinit var presenter: MainContract.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        presenter = Presenter(this, sharedPreferences)
        presenter.loadState()
        initListeners()
    }

    override fun onPause() {
        presenter.saveState()
        super.onPause()
    }

    private fun initListeners() {
        next.setOnClickListener {
            presenter.onNextClicked()
        }

        previous.setOnClickListener {
            presenter.onPrevClicked()
        }

        reset.setOnClickListener {
            presenter.onResetClicked()
        }

        findResult.setOnClickListener {
            presenter.onFindResultClicked()
        }

        textNumber.addTextChangedListener(presenter.textChanged())
    }

    override fun getErrorNotFound(): String = resources.getString(R.string.notFoundNumber)

    override fun getErrorWrongNumber(): String = resources.getString(R.string.wrongNumber)

    override fun getColorNotFound(): Int = resources.getColor(R.color.black, null)

    override fun getColorWrongNumber(): Int = resources.getColor(R.color.errorRed, null)

    override fun setCurrentNumber(number: String) {
        currentNumber.text = number
    }

    override fun setPreviousNumber(number: String) {
        previousNumber.text = number
    }

    override fun setNextNumber(number: String) {
        nextNumber.text = number
    }

    override fun togglePrev(state: Boolean) {
        previous.isClickable = state
        previous.isEnabled = state
    }

    override fun toggleNext(state: Boolean) {
        next.isClickable = state
        next.isEnabled = state
    }

    override fun toggleFindResult(state: Boolean) {
        findResult.isClickable = state
        findResult.isEnabled = state
    }

    override fun setTextNumber(newText: String) {
        textNumber.setText(newText)
    }

    override fun setErrorMessageText(newMessage: String) {
        errorMessage.text = newMessage
    }

    override fun setErrorMessageColor(newColor: Int) {
        errorMessage.setTextColor(newColor)
    }
}