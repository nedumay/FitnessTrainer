package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationTwo : AppCompatActivity() {

    lateinit var registrationActivity: ActivityRegistrationTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationActivity = ActivityRegistrationTwoBinding.inflate(layoutInflater)
        setContentView(registrationActivity.root)

        val backView: ImageView = findViewById(R.id.arrow_back)
        val nextButton: Button = findViewById(R.id.next_btn)

        nextButton.isEnabled = false

        registrationActivity.cardMale.setOnClickListener {
            registrationActivity.cardMale.isChecked = true
            registrationActivity.cardFmale.isChecked = false
            nextButton.isEnabled = true
        }

        registrationActivity.cardFmale.setOnClickListener {
            registrationActivity.cardFmale.isChecked = true
            registrationActivity.cardMale.isChecked = false
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