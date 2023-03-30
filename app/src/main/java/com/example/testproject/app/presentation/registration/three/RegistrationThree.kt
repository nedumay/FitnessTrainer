package com.example.testproject.app.presentation.registration.three

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.registration.four.RegistrationFour
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.databinding.ActivityRegistrationThreeBinding

class RegistrationThree : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationThreeBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: RegistrationThreeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistrationThreeViewModel::class.java]
        var name = intent.getStringExtra(EXTRA_NAME)
        var date = intent.getStringExtra(EXTRA_DATE)
        var gender = intent.getBooleanExtra(EXTRA_GENDER,false)
        var height = binding.editTextHeight.text?.trim().toString()
        var weight = binding.editTextWeight.text?.trim().toString()
        var targetWeight = binding.editTextTarget.text?.trim().toString()

        viewModel.save(name!!,date!!,gender, weight,height,targetWeight)
        viewModel.name.observe(this){
            name = it
        }
        viewModel.dataOfBirth.observe(this){
            date = it
        }
        viewModel.gender.observe(this){
            gender = it
        }
        viewModel.height_.observe(this){
            height = it
        }
        viewModel.weight_.observe(this){
            weight = it
        }
        viewModel.targetWeight.observe(this){
            targetWeight = it
        }
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
            val intent = Intent(context, RegistrationTwo::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            return intent
        }
    }
}