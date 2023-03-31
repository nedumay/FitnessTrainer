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
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.databinding.ActivityResetBinding

class ResetActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResetBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: ResetViewModel

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ResetViewModel::class.java]
        var email = binding.editTextEmailLogin.text?.trim().toString()
        viewModel.save(email)
        viewModel.email.observe(this){
            email = it
        }

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.buttonSend.isEnabled = false
        enabledButton()
        binding.buttonSend.setOnClickListener {
            viewModel.resetPassord(email)
        }
    }
    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonSend.isEnabled = (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
    companion object{
        fun newIntent(context: Context) : Intent{
            return Intent(context,ResetActivity::class.java)
        }
    }
}