package com.example.testproject.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.testproject.R
import com.example.testproject.ui.registration.RegistrationActivity
import com.example.testproject.ui.reset.ResetActivity

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val forgotText: TextView = findViewById(R.id.forgot_your)

        val registrationButton: Button = findViewById(R.id.registr_btn)

        forgotText.setOnClickListener {
            val intentReset = Intent(this@LoginActivity, ResetActivity::class.java)
            startActivity(intentReset)
        }

        registrationButton.setOnClickListener {
            val intentRegistration = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intentRegistration)
        }

    }

}