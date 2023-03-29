package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.app.utils.DataMask
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {

    private val dataMask = DataMask()

    private val binding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editDateOfBirth.addTextChangedListener(dataMask)

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

        binding.buttonNextRegistration.isEnabled = false
        enabledButton()

        binding.buttonNextRegistration.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, RegistrationTwo::class.java))
        }
    }

    // Переделать, не парвильно работает
    private fun enabledButton() {
        binding.editTextNameRegistration.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonNextRegistration.isEnabled =
                    binding.editTextNameRegistration.text?.length ?: 0 > 0
                            && binding.editDateOfBirth.text?.length ?: 0 == 10

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.editDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.buttonNextRegistration.isEnabled =
                    binding.editDateOfBirth.text?.length ?: 0 == 10
                            && binding.editTextNameRegistration.text?.length ?: 0 > 0
            }
        })

    }
}