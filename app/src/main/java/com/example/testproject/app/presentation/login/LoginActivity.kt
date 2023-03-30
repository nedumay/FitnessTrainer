package com.example.testproject.app.presentation.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textViewForgotPassword.setOnClickListener {
            startActivity(RegistrationOne.newIntent(this@LoginActivity))
        }
        binding.buttonRegistrationLogin.setOnClickListener {
            startActivity(RegistrationOne.newIntent(this@LoginActivity))
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

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val valid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if (!valid) {
                    binding.tilEmailLogin.error = INVALID_ADDRESS
                } else {
                    binding.tilEmailLogin.error = EMPTY_FIELD
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.editTextPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextPasswordLogin.text?.length
                    ?: 0) > 0 && (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    companion object {
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val EMPTY_FIELD = ""

        fun newIntent(context: Context) : Intent{
            return Intent(context,LoginActivity::class.java)
        }
    }

}