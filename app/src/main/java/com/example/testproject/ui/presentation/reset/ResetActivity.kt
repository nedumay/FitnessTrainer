package com.example.testproject.ui.presentation.reset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.testproject.R
import com.example.testproject.ui.presentation.login.LoginActivity
import com.example.testproject.ui.presentation.main.MainActivity

class ResetActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var enterButton: Button
    private lateinit var backButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        inputText = findViewById(R.id.input_name_reg)
        enterButton = findViewById(R.id.enter_btn)
        backButton = findViewById(R.id.arrow_back)

        backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        enterButton.isEnabled = false
        enabledButton()
    }

    private fun enabledButton() {
        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                enterButton.isEnabled = inputText.text.length > 0
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}