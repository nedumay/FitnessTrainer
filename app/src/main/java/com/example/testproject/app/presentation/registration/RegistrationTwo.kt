package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationTwo : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationTwoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonNextRegistration.isEnabled = false

        binding.cardViewMale.setOnClickListener {
            binding.cardViewMale.isChecked = true
            binding.cardViewMale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
        }

        binding.cardViewFmale.setOnClickListener {
            binding.cardViewFmale.isChecked = true
            binding.cardViewFmale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
        }


        binding.imageButtonArrowBack.setOnClickListener {
            val intentLogin = Intent(this, RegistrationActivity::class.java)
            startActivity(intentLogin)
        }

        binding.buttonNextRegistration.setOnClickListener {
            val intentRegTwo = Intent(this, RegistrationThree::class.java)
            startActivity(intentRegTwo)
        }

    }
}