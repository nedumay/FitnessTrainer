package com.example.testproject.app.presentation.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.main.MainActivity
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.app.presentation.reset.ResetActivity
import com.example.testproject.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.textViewForgotPassword.setOnClickListener {
            startActivity(ResetActivity.newIntent(this@LoginActivity))
        }
        binding.buttonRegistrationLogin.setOnClickListener {
            startActivity(RegistrationOne.newIntent(this@LoginActivity))
        }
        binding.buttonEnterLogin.isEnabled = false
        enabledButton()
        observeViewModel()
        binding.buttonEnterLogin.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            val password = binding.editTextPasswordLogin.text?.trim().toString()
            viewModel.login(email,password)
        }
    }

    private fun observeViewModel() {
        viewModel.error.observe(this){
            if(it != null){
                Toast.makeText(this@LoginActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.firebaseUser.observe(this){
            if(it != null){
                Log.d("Login user fb","Start Activity: $it")
                startActivity(
                    DashboardActivity.newIntent(this@LoginActivity, it)
                )
                finish()
            }
        }
    }

    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextEmailLogin.text?.length
                    ?: 0) > 0 && (binding.editTextPasswordLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val valid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if (!valid) {
                    binding.tilEmailLogin.error = INVALID_ADDRESS
                } else {
                    binding.tilEmailLogin.error = EMPTY_FIELD
                }
            }
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
            val intent = Intent(context,LoginActivity::class.java)
            return intent
        }
    }

}