package com.example.testproject.app.presentation.registration.three

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
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

        var sufWeight = resources.getString(R.string.ft)
        var sufHeight = resources.getString(R.string.lbs)
        val name = intent.getStringExtra(EXTRA_NAME)
        val lastName = intent.getStringExtra(EXTRA_LAST_NAME)
        val date = intent.getStringExtra(EXTRA_DATE)
        val gender = intent.getBooleanExtra(EXTRA_GENDER,false)

        Log.d("RegistrationActivity", "Three activity get: $name, $lastName, $date, $gender")

        binding.switchBtn.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                if (checkedId == R.id.lbsRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.lbs)
                    binding.tilWeight.suffixText = resources.getString(R.string.ft)
                    binding.tilTarget.suffixText = resources.getString(R.string.ft)
                    sufWeight = resources.getString(R.string.ft)
                    sufHeight = resources.getString(R.string.lbs)
                } else if (checkedId == R.id.kgRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.sm)
                    binding.tilWeight.suffixText = resources.getString(R.string.kg)
                    binding.tilTarget.suffixText = resources.getString(R.string.kg)
                    sufWeight = resources.getString(R.string.kg)
                    sufHeight = resources.getString(R.string.sm)
                }
            }
        }

        binding.buttonNextRegistration.setOnClickListener {
            val height = binding.editTextHeight.text?.trim().toString() + sufHeight
            val weight = binding.editTextWeight.text?.trim().toString() + sufWeight
            val targetWeight = binding.editTextTarget.text?.trim().toString() + sufWeight
            Log.d("RegistrationActivity",
                "Three activity extra: $name, $date, $gender, $height, $weight, $targetWeight")
            startActivity(
                RegistrationFour.newIntent
                    (
                    this@RegistrationThree,
                    name = name!!,
                    lastName = lastName!!,
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
                    lastName = lastName!!,
                    date = date!!
                )
            )
            finish()
        }
    }

    companion object {
        private const val EXTRA_NAME = "name"
        private const val EXTRA_LAST_NAME = "last_name"
        private const val EXTRA_DATE = "date"
        private const val EXTRA_GENDER = "gender"
        fun newIntent(context: Context, name: String,lastName: String, date: String, gender: Boolean): Intent {
            val intent = Intent(context, RegistrationThree::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_LAST_NAME, lastName)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            return intent
        }
    }
}