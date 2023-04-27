package com.example.testproject.app.presentation.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.app.presentation.reset.ResetActivity
import com.example.testproject.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        binding.buttonEnterLogin.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            val password = binding.editTextPasswordLogin.text?.trim().toString()
            viewModel.login(email, password)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        val progressDialog = ProgressDialog(this@LoginActivity)
        val alertDialog = AlertDialog.Builder(this@LoginActivity)
        viewModel.firebaseUser.onEach {
            when(it){
                is Resource.Loading ->{
                    Log.d("LoginActivity", "Loading: $it")
                    progressDialog.setTitle("Login to your account")
                    progressDialog.setMessage("Please, wait...")
                    progressDialog.isIndeterminate = true
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                is Resource.Success ->{
                    Log.d("LoginActivity", "Success: ${it.data}")
                    progressDialog.dismiss()
                    startActivity(DashboardActivity.newIntent(this@LoginActivity, it.data))
                    finish()
                }
                is Resource.Error ->{
                    Log.d("LoginActivity", "Error: $it")
                    progressDialog.dismiss()
                    alertDialog.setTitle("Error")
                    alertDialog.setIcon(R.drawable.ic_error)
                    alertDialog.setMessage("$it")
                    alertDialog.setPositiveButton("OK"){ dialog, which ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }
            }
        }.launchIn(lifecycleScope)
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

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

}