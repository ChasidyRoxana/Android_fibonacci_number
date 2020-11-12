package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private var presenter: MainContract.MainPresenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.i("NAIDIERROR", "super")
        super.onCreate(savedInstanceState)
//        Log.i("NAIDIERROR", "setContentView")
        setContentView(R.layout.activity_main)
//        Log.i("NAIDIERROR", "go to loadState")
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        presenter.setSharedPref(sharedPreferences)
        presenter.loadState()
//        Log.i("NAIDIERROR", "bishel is loadState")
        initListeners()
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
            presenter.onFindResultClicked(textNumber.text.toString())
        }
    }

    override fun onPause() {
        presenter.saveState()
        super.onPause()
    }

    override fun setCurrentNumber(number: String) {
        currentNumber.text = number
    }

    override fun setPreviousNumber(number: String) {
        previousNumber.text = number
    }

    override fun setNextNumber(number: String) {
        nextNumber.text = number
    }

    override fun setPrevClickable(isClickable: Boolean) {
        previous.isClickable = isClickable
        previous.isEnabled = isClickable
    }

    override fun setNextClickable(isClickable: Boolean) {
        next.isClickable = isClickable
        next.isEnabled = isClickable
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

    override fun getErrorNotFound(): String = resources.getString(R.string.notFoundNumber)

    override fun getErrorWrongNumber(): String = resources.getString(R.string.wrongNumber)

    override fun getColorNotFound(): Int = resources.getColor(R.color.black, null)

    override fun getColorWrongNumber(): Int = resources.getColor(R.color.errorRed, null)
}