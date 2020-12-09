package com.example.fibonaccinumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.mainFragment, MainFragment())
            }
        }
    }

    override fun onDestroy() {
        Log.i("TUT", "destroy activity")
        super.onDestroy()
//        supportFragmentManager.beginTransaction().remove(mainFragment).commit()
    }

}