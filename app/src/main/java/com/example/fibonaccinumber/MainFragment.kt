package com.example.fibonaccinumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), MainContract.MainView {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var presenter: MainContract.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val sharedPreferences = activity.getPreferences(AppCompatActivity.MODE_PRIVATE)
        presenter = Presenter(this, sharedPreferences)
        presenter.loadState()
        initListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onPause() {
        super.onPause()
        presenter.saveState()
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
    }

    override fun setPreviousNumber(number: String) {
        previousNumber.text = number
    }

    override fun setNextNumber(number: String) {
        nextNumber.text = number
    }

    override fun togglePrev(state: Boolean) {
        previous.isEnabled = state
    }

    override fun toggleNext(state: Boolean) {
        next.isEnabled = state
    }

    override fun toggleFindResult(state: Boolean) {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters

        // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}