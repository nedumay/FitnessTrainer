package com.example.testproject.app.presentation.registration

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.testproject.R
import com.google.android.material.snackbar.Snackbar

class RegistrationFour : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_four)



        val agreementTextView: TextView = findViewById(R.id.by_creating)
        val backButton: ImageView = findViewById(R.id.arrowBack)
        val creatAccountButton: Button = findViewById(R.id.create_account_btn)
        val loginInput: EditText = findViewById(R.id.input_name_reg)
        val passwordInput: EditText = findViewById(R.id.input_data_of_birth)

        val fullText = getString(R.string.by_creating)
        val policy = getString(R.string.privacy_policy)
        val terms = getString(R.string.terms_of_payment)
        val spannableString = SpannableString(fullText)

        // Кликабельный текст для Privacy policy
        val policyClickable = object : ClickableSpan(){
            override fun onClick(widget: View) {
                Snackbar.make(widget, "Privacy policy", Snackbar.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#94D647")
            }
        }
        spannableString.setSpan(
            policyClickable,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Кликабельный текст для Terms
        val termsClickable = object : ClickableSpan(){
            override fun onClick(widget: View) {
                Snackbar.make(widget, "Terms of payment and return", Snackbar.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#94D647")
            }
        }
        spannableString.setSpan(
            termsClickable,
            fullText.indexOf(terms),
            fullText.indexOf(terms) + terms.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        agreementTextView.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        backButton.setOnClickListener {
            val intent = Intent(this, RegistrationThree::class.java)
            startActivity(intent)
        }

        creatAccountButton.setOnClickListener {

        }
    }
}