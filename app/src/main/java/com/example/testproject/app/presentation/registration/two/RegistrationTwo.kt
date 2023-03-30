package com.example.testproject.app.presentation.registration.two

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationTwo : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationTwoBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: RegistrationTwoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrationTwoViewModel::class.java]
        if(!intent.hasExtra(EXTRA_NAME) && !intent.hasExtra(EXTRA_DATE)){
            finish()
            return
        }
        var name = intent.getStringExtra(EXTRA_NAME)
        var date = intent.getStringExtra(EXTRA_DATE)
        var gender = false

        binding.buttonNextRegistration.isEnabled = false

        binding.cardViewMale.setOnClickListener {
            binding.cardViewMale.isChecked = true
            binding.cardViewFmale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
            gender = true
        }

        binding.cardViewFmale.setOnClickListener {
            binding.cardViewFmale.isChecked = true
            binding.cardViewMale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
            gender = false
        }

        viewModel.save(name!!,date!!, gender)
        viewModel.name.observe(this) {
            name = it
        }
        viewModel.dataOfBirth.observe(this) {
            date = it
        }
        viewModel.gender.observe(this) {
            gender = it
        }

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(RegistrationOne.newIntent(this@RegistrationTwo))
        }

        binding.buttonNextRegistration.setOnClickListener {
            startActivity(
                RegistrationThree.newIntent(
                    this@RegistrationTwo,
                    name = name!!,
                    date = date!!,
                    gender = gender
                )
            )
        }

    }
    companion object {
        private const val EXTRA_NAME = "name"
        private const val EXTRA_DATE = "date"
        fun newIntent(context: Context, name: String, date: String): Intent {
            val intent = Intent(context, RegistrationTwo::class.java)
            intent.putExtra(EXTRA_NAME,name)
            intent.putExtra(EXTRA_DATE,date)
            return intent
        }
    }
}