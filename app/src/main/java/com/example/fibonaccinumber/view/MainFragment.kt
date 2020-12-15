package com.example.fibonaccinumber.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.R
import com.example.fibonaccinumber.presenter.Presenter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainContract.MainView {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenter: MainContract.MainPresenter
    private lateinit var activityContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        sharedPreferences =
            context.getSharedPreferences("preferences", AppCompatActivity.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = Presenter(this, sharedPreferences)
        presenter.loadState(savedInstanceState)
        initListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveState(outState)
    }

    private fun initListeners() {
        ibNext.setOnClickListener {
            presenter.onNextClicked()
        }

        ibPrevious.setOnClickListener {
            presenter.onPrevClicked()
        }

        ibReset.setOnClickListener {
            presenter.onResetClicked()
        }

        bFindResult.setOnClickListener {
            presenter.onFindResultClicked()
        }

        tvEnterNumber.addTextChangedListener(presenter.textChanged())

        tvEnterNumber.setOnEditorActionListener(presenter.editorAction())
    }

    override fun getErrorNotFound(): String = resources.getString(R.string.notFoundNumber)

    override fun getErrorWrongNumber(): String = resources.getString(R.string.wrongNumber)

    override fun getErrorEmptyString(): String = resources.getString(R.string.emptyString)

    override fun getBlackColor(): Int = resources.getColor(R.color.black, null)

    override fun getRedColor(): Int = resources.getColor(R.color.errorRed, null)

    override fun setCurrentNumber(number: String) {
        tvCurrentNumber.text = number
    }

    override fun setPreviousNumber(number: String) {
        tvPreviousNumber.text = number
    }

    override fun setNextNumber(number: String) {
        tvNextNumber.text = number
    }

    override fun clearEditText() {
        tvEnterNumber.clearFocus()
        val imm =
            activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tvEnterNumber.windowToken, 0)
    }

    override fun togglePrev(state: Boolean) {
        ibPrevious.isEnabled = state
    }

    override fun toggleNext(state: Boolean) {
        ibNext.isEnabled = state
    }

    override fun toggleFindResult(state: Boolean) {
        bFindResult.isEnabled = state
    }

    override fun setEnterNumber(newText: String) {
        tvEnterNumber.setText(newText)
    }

    override fun setErrorMessageText(newMessage: String) {
        tvErrorMessage.text = newMessage
    }

    override fun setErrorMessageColor(newColor: Int) {
        tvErrorMessage.setTextColor(newColor)
    }

}