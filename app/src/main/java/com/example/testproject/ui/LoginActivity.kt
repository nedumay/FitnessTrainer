package com.example.testproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.testproject.R

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotText: TextView = findViewById(R.id.forgot_your)

        forgotText.setOnClickListener {
            val intetnReset = Intent(this, ResetActivity::class.java)
            startActivity(intetnReset)
        }

    }

}