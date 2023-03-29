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
import com.example.testproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textViewForgotPassword.setOnClickListener {
            val intentReset = Intent(this@LoginActivity, ResetActivity::class.java)
            startActivity(intentReset)
        }

        binding.buttonRegistrationLogin.setOnClickListener {
            val intentRegistration = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intentRegistration)
        }

        binding.buttonEnterLogin.isEnabled = false
        enabledButton()

        binding.buttonEnterLogin.setOnClickListener {
            val intentDashboard = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intentDashboard)
        }

    }

    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextEmailLogin.text?.length
                    ?: 0) > 0 && (binding.editTextPasswordLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.editTextPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextPasswordLogin.text?.length
                    ?: 0) > 0 && (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

}