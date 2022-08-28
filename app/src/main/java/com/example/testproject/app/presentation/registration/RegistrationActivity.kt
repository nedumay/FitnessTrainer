package com.example.testproject.app.presentation.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.app.domain.DataMask
import com.example.testproject.app.presentation.login.LoginActivity


class RegistrationActivity : AppCompatActivity() {

    private lateinit var inputTextName: EditText
    private lateinit var inputTextData: EditText
    private lateinit var nextButton: Button

    private val dataMask = DataMask()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val backView: ImageView = findViewById(R.id.arrowBack)
        nextButton = findViewById(R.id.nextBtn)
        inputTextName = findViewById(R.id.input_name_reg)
        inputTextData = findViewById(R.id.input_data_of_birth)

        inputTextData.addTextChangedListener(dataMask)


        backView.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

        nextButton.isEnabled = false
        enabledButton()

        nextButton.setOnClickListener {
            startActivity( Intent(this@RegistrationActivity, RegistrationTwo::class.java))
        }
    }

    // Переделать, не парвильно работает


    private fun enabledButton() {
        inputTextName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                nextButton.isEnabled = inputTextName.text.length > 0 && inputTextData.text.length == 10

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        inputTextData.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                nextButton.isEnabled = inputTextData.text.length == 10 && inputTextName.text.length > 0
            }
        })

    }
}