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
        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(LoginActivity.newIntent(this@RegistrationOne))
        }
        binding.buttonNextRegistration.isEnabled = false
        enabledButton()
        // Исправить name и date
        var name = binding.editTextNameRegistration.text?.trim().toString()
        var data = binding.editDateOfBirth.text?.trim().toString()
        val pair = observeInit(name, data)
        data = pair.first
        name = pair.second
        binding.buttonNextRegistration.setOnClickListener {
            startActivity(
                RegistrationTwo.newIntent(
                    this@RegistrationOne,
                    name,
                    data
                )
            )
        }
    }

    private fun observeInit(
        name: String,
        data: String
    ): Pair<String, String> {
        var name1 = name
        var data1 = data
        viewModel.save(name1, data1)
        viewModel.name.observe(this) {
            name1 = it
        }
        viewModel.dataOfBirth.observe(this) {
            data1 = it
        }
        return Pair(data1, name1)
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