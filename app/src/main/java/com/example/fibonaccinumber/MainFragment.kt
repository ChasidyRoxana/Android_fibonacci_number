package com.example.fibonaccinumber

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainContract.MainView {

//    private var param1: String? = null
//    private var param2: String? = null

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenter: MainContract.MainPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("preferences", AppCompatActivity.MODE_PRIVATE)
        Log.i("TUT", "fragment attach $context")
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        arguments?.let {
////            param1 = it.getString(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
////        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = Presenter(this, sharedPreferences)
        Log.i("TUT", "load state")
        presenter.loadState()
        initListeners()
    }

    override fun onPause() {
        super.onPause()
        Log.i("TUT", "save state")
        presenter.saveState()
    }

    override fun onDestroy() {
        Log.i("TUT", "destroy fragment")
        super.onDestroy()
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

        textNumber.setOnEditorActionListener(presenter.imeAction())
    }

    override fun getErrorNotFound(): String = resources.getString(R.string.notFoundNumber)

    override fun getErrorWrongNumber(): String = resources.getString(R.string.wrongNumber)

    override fun getErrorEmptyString(): String = resources.getString(R.string.emptyString)

    override fun getBlackColor(): Int = resources.getColor(R.color.black, null)

    override fun getRedColor(): Int = resources.getColor(R.color.errorRed, null)

    override fun setCurrentNumber(number: String) {
        currentNumber.text = number
        Log.i("TUT", "change curr num")
    }

    override fun setPreviousNumber(number: String) {
        previousNumber.text = number
        Log.i("TUT", "change prev num")
    }

    override fun setNextNumber(number: String) {
        nextNumber.text = number
        Log.i("TUT", "change next num")
    }

    override fun togglePrev(state: Boolean) {
        previous.isEnabled = state
        Log.i("TUT", "toggle prev")
    }

    override fun toggleNext(state: Boolean) {
        next.isEnabled = state
        Log.i("TUT", "toggle next")
    }

    override fun toggleFindResult(state: Boolean) {
        findResult.isEnabled = state
    }

    override fun setTextNumber(newText: String) {
        textNumber.setText(newText)
        Log.i("TUT", "change editText num")
    }

    override fun setErrorMessageText(newMessage: String) {
        errorMessage.text = newMessage
    }

    override fun setErrorMessageColor(newColor: Int) {
        errorMessage.setTextColor(newColor)
    }

//    companion object {
//        private const val ARG_PARAM1 = "param1"
//        private const val ARG_PARAM2 = "param2"

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MainFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}