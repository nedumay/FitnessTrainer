package com.example.testproject.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.ui.login.LoginActivity

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val backView: ImageView = findViewById(R.id.arrow_back)
        val nextButton: Button = findViewById(R.id.next_btn)

        backView.setOnClickListener {
            val intentLogin = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        nextButton.setOnClickListener {
            val intentRegTwo = Intent(this@RegistrationActivity, RegistrationTwo::class.java)
            startActivity(intentRegTwo)
        }
    }
}