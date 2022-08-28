package com.example.testproject.app.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.example.testproject.R
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.registration.RegistrationActivity
import com.example.testproject.app.presentation.reset.ResetActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var inputText: TextView
    private lateinit var inputPassword: TextView
    private lateinit var enterLoginBtn: Button

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

        inputText = findViewById(R.id.input_name_reg)
        inputPassword = findViewById(R.id.input_data_of_birth)
        enterLoginBtn = findViewById(R.id.enter_login_btn)

        enterLoginBtn.isEnabled = false
        enabledButton()

        enterLoginBtn.setOnClickListener {
            // тест
            val intentDashboard = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intentDashboard)
        }

    }

    private fun enabledButton() {
        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                enterLoginBtn.isEnabled = inputText.text.length > 0 && inputPassword.text.length > 0
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        inputPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                enterLoginBtn.isEnabled = inputPassword.text.length > 0 && inputText.text.length > 0
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

}