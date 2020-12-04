package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private lateinit var presenter: MainContract.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainFragment, MainFragment())
                .commit()
        }
//        val sharedPreferences = getPreferences(MODE_PRIVATE)
//        presenter = Presenter(this, sharedPreferences)
//        presenter.loadState()
//        initListeners()
    }

}