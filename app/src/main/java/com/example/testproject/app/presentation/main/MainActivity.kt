package com.example.testproject.app.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.R
import com.example.testproject.app.presentation.login.LoginActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            startActivity(LoginActivity.newIntent(this@MainActivity))
            finish()
        }, 5000)

    }
}