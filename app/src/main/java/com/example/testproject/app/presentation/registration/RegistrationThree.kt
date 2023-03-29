package com.example.testproject.app.presentation.registration

import android.content.Context
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
            //Исправить передачу данных. Заменить переменные name, date
            startActivity(
                RegistrationTwo.newIntent(
                    this@RegistrationThree,
                    name = "",
                    date = ""
                )
            )
        }

        binding.buttonNextRegistration.setOnClickListener {
            //Исправить передачу данных. Заменить переменные name, date, gender, height, weight, targetWeight
            startActivity(
                RegistrationFour.newIntent
                    (
                    this@RegistrationThree,
                    name = "",
                    date = "",
                    gender = false,
                    height = "",
                    weight = "",
                    targetWeight = ""

                )
            )
        }
    }

    companion object {
        private const val EXTRA_NAME = "name"
        private const val EXTRA_DATE = "date"
        private const val EXTRA_GENDER = "gender"
        fun newIntent(context: Context, name: String, date: String, gender: Boolean): Intent {
            val intent = Intent(context, RegistrationTwo::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            return intent
        }
    }
}