package com.example.testproject.app.presentation.registration.three

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.registration.four.RegistrationFour
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.databinding.ActivityRegistrationThreeBinding

class RegistrationThree : AppCompatActivity() {

    private val binding by lazy { ActivityRegistrationThreeBinding.inflate(layoutInflater) }

    private val component by lazy { (application as App).component }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val date = intent.getStringExtra(EXTRA_DATE)
        val gender = intent.getBooleanExtra(EXTRA_GENDER,false)

        Log.d("RegistrationActivity", "Three activity get: $name, $date, $gender")

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

        binding.buttonNextRegistration.setOnClickListener {
            val height = binding.editTextHeight.text?.trim().toString()
            val weight = binding.editTextWeight.text?.trim().toString()
            val targetWeight = binding.editTextTarget.text?.trim().toString()
            Log.d("RegistrationActivity",
                "Three activity extra: $name, $date, $gender, $height, $weight, $targetWeight")
            startActivity(
                RegistrationFour.newIntent
                    (
                    this@RegistrationThree,
                    name = name!!,
                    date = date!!,
                    gender = gender,
                    height = height,
                    weight = weight,
                    targetWeight = targetWeight

                )
            )
        }

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(
                RegistrationTwo.newIntent(
                    this@RegistrationThree,
                    name = name!!,
                    date = date!!
                )
            )
        }
    }

    companion object {
        private const val EXTRA_NAME = "name"
        private const val EXTRA_DATE = "date"
        private const val EXTRA_GENDER = "gender"
        fun newIntent(context: Context, name: String, date: String, gender: Boolean): Intent {
            val intent = Intent(context, RegistrationThree::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            return intent
        }
    }
}