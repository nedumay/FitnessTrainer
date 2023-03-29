package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import com.example.testproject.R
import com.example.testproject.databinding.ActivityRegistrationThreeBinding
import com.example.testproject.databinding.ActivityRegistrationTwoBinding

class RegistrationThree : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationThreeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.switchBtn.setOnCheckedChangeListener { _, checkedId ->

            findViewById<RadioButton>(checkedId)?.apply {
                if (checkedId == R.id.lbsRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.lbs)
                    binding.tilWeight.suffixText = resources.getString(R.string.ft)
                    binding.tilTarget.suffixText = resources.getString(R.string.ft)
                } else if (checkedId == R.id.kgRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.sm)
                    binding.tilWeight.suffixText = resources.getString(R.string.kg)
                    binding.tilTarget.suffixText = resources.getString(R.string.kg)
                }
            }
        }

        binding.imageButtonArrowBack.setOnClickListener {
            val intentLogin = Intent(this, RegistrationTwo::class.java)
            startActivity(intentLogin)
        }

        binding.buttonNextRegistration.setOnClickListener {
            val intentRegTwo = Intent(this, RegistrationFour::class.java)
            startActivity(intentRegTwo)
        }
    }
}