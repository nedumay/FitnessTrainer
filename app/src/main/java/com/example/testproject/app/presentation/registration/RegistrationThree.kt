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

    private lateinit var binding: ActivityRegistrationThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchBtn.setOnCheckedChangeListener{ _, checkedId ->

            findViewById<RadioButton>(checkedId)?.apply {
                if(checkedId == R.id.lbsRadioBtn){
                    binding.textHeight.suffixText = resources.getString(R.string.lbs)
                    binding.textWeight.suffixText = resources.getString(R.string.ft)
                    binding.textTarget.suffixText = resources.getString(R.string.ft)
                } else if (checkedId == R.id.kgRadioBtn){
                    binding.textHeight.suffixText = resources.getString(R.string.sm)
                    binding.textWeight.suffixText = resources.getString(R.string.kg)
                    binding.textTarget.suffixText = resources.getString(R.string.kg)
                }
            }
        }

        binding.arrowBack.setOnClickListener {
            val intentLogin = Intent(this, RegistrationTwo::class.java)
            startActivity(intentLogin)
        }

        binding.nextBtn.setOnClickListener {
            val intentRegTwo = Intent(this, RegistrationFour::class.java)
            startActivity(intentRegTwo)
        }
    }
}