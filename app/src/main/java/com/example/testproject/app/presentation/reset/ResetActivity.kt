package com.example.testproject.app.presentation.reset

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.databinding.ActivityResetBinding
import javax.inject.Inject

class ResetActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResetBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ResetViewModel

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,viewModelFactory)[ResetViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.buttonSend.isEnabled = false
        enabledButton()
        binding.buttonSend.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            viewModel.resetPassord(email)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.error.observe(this) {
            if (it != null) {
                Toast.makeText(this@ResetActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonSend.isEnabled = (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val valid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if (!valid) {
                    binding.tilEmailReset.error = INVALID_ADDRESS
                } else {
                    binding.tilEmailReset.error = EMPTY_FIELD
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
    companion object{
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val EMPTY_FIELD = ""
        fun newIntent(context: Context) : Intent{
            return Intent(context,ResetActivity::class.java)
        }
    }
}