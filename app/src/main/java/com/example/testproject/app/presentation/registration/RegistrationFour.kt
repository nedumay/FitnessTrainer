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
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.databinding.ActivityRegistrationFourBinding
import com.google.android.material.snackbar.Snackbar

class RegistrationFour : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationFourBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fullText = getString(R.string.by_creating)
        val policy = getString(R.string.privacy_policy)
        val terms = getString(R.string.terms_of_payment)
        val spannableString = SpannableString(fullText)

        // Кликабельный текст для Privacy policy
        val policyClickable = object : ClickableSpan() {
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
        val termsClickable = object : ClickableSpan() {
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
        binding.textViewByCreate.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        binding.imageButtonArrowBack.setOnClickListener {
            val intent = Intent(this, RegistrationThree::class.java)
            startActivity(intent)
        }

        binding.buttonCreateAccount.setOnClickListener {
            Toast.makeText(this@RegistrationFour, "Create account!", Toast.LENGTH_LONG).show()
        }
    }
}