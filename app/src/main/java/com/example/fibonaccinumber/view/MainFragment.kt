package com.example.fibonaccinumber.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
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
        presenter = Presenter(this, resources, app.repository, app.fibonacciNumbers)
        initListeners()
    }

    private fun initListeners() {
        ibNext.setOnClickListener { presenter.onNextClicked() }

        ibPrevious.setOnClickListener { presenter.onPrevClicked() }

        ibReset.setOnClickListener { presenter.onResetClicked() }

        bFindResult.setOnClickListener { presenter.onFindResultClicked() }

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

    override fun clearEditTextAndCloseKeyboard() {
        etEnterNumber.clearFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etEnterNumber.windowToken, 0)
    }

    override fun setEnabledPrevButton(isEnabled: Boolean) {
        ibPrevious.isEnabled = isEnabled
    }

    override fun setEnabledNextButton(isEnabled: Boolean) {
        ibNext.isEnabled = isEnabled
    }

    override fun setEnabledFindResultButton(isEnabled: Boolean) {
        bFindResult.isEnabled = isEnabled
    }

    override fun setEnterNumber(text: String) {
        etEnterNumber.setText(text)
    }

    override fun setErrorMessageText(message: String) {
        tvErrorMessage.text = message
    }

    override fun setErrorMessageColor(colorId: Int) {
        val colorValue = requireContext().let { ContextCompat.getColor(it, colorId) }
        tvErrorMessage.setTextColor(colorValue)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}