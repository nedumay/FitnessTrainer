package com.example.testproject.app.presentation.registration.one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.app.utils.DataMask
import com.example.testproject.databinding.ActivityRegistrationOneBinding


class RegistrationOne : AppCompatActivity() {

    private val dataMask = DataMask()

    private val binding by lazy {
        ActivityRegistrationOneBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editDateOfBirth.addTextChangedListener(dataMask)

        binding.buttonNextRegistration.isEnabled = false
        enabledButton()

        binding.buttonNextRegistration.setOnClickListener {
            val name = binding.editTextNameRegistration.text?.trim().toString()
            val date = binding.editDateOfBirth.text?.trim().toString()
            Log.d("RegistrationActivity", "One activity extra: $name, $date ")
            startActivity(RegistrationTwo.newIntent(this@RegistrationOne, name, date))
        }
        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(LoginActivity.newIntent(this@RegistrationOne))
        }
    }

    private fun enabledButton() {
        binding.editTextNameRegistration.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonNextRegistration.isEnabled =
                    ((binding.editTextNameRegistration.text?.length ?: 0) > 0
                            && (binding.editDateOfBirth.text?.length ?: 0) == 10)

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        binding.editDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonNextRegistration.isEnabled =
                    ((binding.editDateOfBirth.text?.length ?: 0) == 10
                            && (binding.editTextNameRegistration.text?.length ?: 0) > 0)
            }
        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegistrationOne::class.java)
        }
    }
}