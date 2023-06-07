package com.example.testproject.app.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}