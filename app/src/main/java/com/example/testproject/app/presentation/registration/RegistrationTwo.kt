package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.testproject.R
import kotlinx.android.synthetic.main.activity_registration_two.*

class RegistrationTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_two)

        val backView: ImageView = findViewById(R.id.arrow_back)
        val nextButton: Button = findViewById(R.id.next_btn)

        nextButton.isEnabled = false

        cardMale.setOnClickListener {
            cardMale.isChecked = true
            cardFmale.isChecked = false
            nextButton.isEnabled = true
        }

        cardFmale.setOnClickListener {
            cardFmale.isChecked = true
            cardMale.isChecked = false
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