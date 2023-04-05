package com.example.testproject.app.presentation.registration.two

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationTwo : AppCompatActivity() {

    private val binding by lazy { ActivityRegistrationTwoBinding.inflate(layoutInflater) }
    private val component by lazy { (application as App).component }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_NAME) && !intent.hasExtra(EXTRA_DATE)) {
            finish()
            return
        }
        val name = intent.getStringExtra(EXTRA_NAME)
        val date = intent.getStringExtra(EXTRA_DATE)
        Log.d("RegistrationActivity", "Two activity get:$name, $date")
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
        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(RegistrationOne.newIntent(this@RegistrationTwo))
        }

        binding.buttonNextRegistration.setOnClickListener {
            Log.d("RegistrationActivity", "Two activity extra: $name, $date, $gender ")
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
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_DATE, date)
            return intent
        }
    }
}