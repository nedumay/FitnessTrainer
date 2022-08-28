package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationTwo : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backView: ImageView = findViewById(R.id.arrowBack)
        val nextButton: Button = findViewById(R.id.nextBtn)

        nextButton.isEnabled = false

        binding.cardMale.setOnClickListener {
            binding.cardMale.isChecked = true
            binding.cardFmale.isChecked = false
            nextButton.isEnabled = true
        }

        binding.cardFmale.setOnClickListener {
            binding.cardFmale.isChecked = true
            binding.cardMale.isChecked = false
            nextButton.isEnabled = true
        }


        backView.setOnClickListener {
            val intentLogin = Intent(this, RegistrationActivity::class.java)
            startActivity(intentLogin)
        }

        nextButton.setOnClickListener {
            val intentRegTwo = Intent(this, RegistrationThree::class.java)
            startActivity(intentRegTwo)
        }

    }
}