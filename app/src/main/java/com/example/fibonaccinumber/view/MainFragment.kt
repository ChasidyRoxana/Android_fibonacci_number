package com.example.fibonaccinumber.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.fibonaccinumber.App
import com.example.fibonaccinumber.MainContract
import com.example.fibonaccinumber.R
import com.example.fibonaccinumber.presenter.Presenter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main), MainContract.MainView {

    private lateinit var presenter: MainContract.MainPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as App
        presenter = Presenter(this, app.repository, resources)
        presenter.onInitialized()
        initListeners()
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

        etEnterNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                presenter.textChanged(s?.toString())
            }
        })

        etEnterNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onFindResultClicked()
                true
            } else {
                false
            }
        }
    }

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
        etEnterNumber.clearFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etEnterNumber.windowToken, 0)
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
        etEnterNumber.setText(newText)
    }

    override fun setErrorMessageText(newMessage: String) {
        tvErrorMessage.text = newMessage
    }

    override fun setErrorMessageColor(newColor: Int) {
        tvErrorMessage.setTextColor(newColor)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}