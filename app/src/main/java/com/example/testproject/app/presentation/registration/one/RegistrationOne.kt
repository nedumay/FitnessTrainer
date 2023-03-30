package com.example.testproject.app.presentation.registration.one

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.app.utils.DataMask
import com.example.testproject.databinding.ActivityRegistrationOneBinding


class RegistrationOne : AppCompatActivity() {

    private val dataMask = DataMask()

    private val binding by lazy {
        ActivityRegistrationOneBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: RegistrationOneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrationOneViewModel::class.java]

        binding.editDateOfBirth.addTextChangedListener(dataMask)

        var name = binding.editTextNameRegistration.text?.trim().toString()
        var date = binding.editDateOfBirth.text?.trim().toString()
        viewModel.save(name,date)
        viewModel.name.observe(this){
            name = it
        }
        viewModel.dataOfBirth.observe(this){
            date = it
        }
        binding.buttonNextRegistration.isEnabled = false
        enabledButton()
        binding.buttonNextRegistration.setOnClickListener {
            startActivity(
                RegistrationTwo.newIntent(
                    this@RegistrationOne,
                    name,
                    date
                )
            )
        }
        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(LoginActivity.newIntent(this@RegistrationOne))
        }
    }

    // Переделать, не парвильно работает
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

        /*
        binding.editDateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonNextRegistration.isEnabled =
                    binding.editDateOfBirth.text?.length ?: 0 == 10
                            && binding.editTextNameRegistration.text?.length ?: 0 > 0
            }
        })*/
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegistrationOne::class.java)
        }
    }
}