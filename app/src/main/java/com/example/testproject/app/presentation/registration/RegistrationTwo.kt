package com.example.testproject.app.presentation.registration

import android.content.Context
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
            startActivity(RegistrationActivity.newIntent(this@RegistrationTwo))
        }

        binding.buttonNextRegistration.setOnClickListener {
            startActivity(
                RegistrationThree.newIntent(
                    this@RegistrationTwo,
                    name = "",
                    date = "",
                    gender = false
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