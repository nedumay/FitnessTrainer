package com.example.testproject.app.presentation.registration.four

import android.content.Context
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
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.databinding.ActivityRegistrationFourBinding
import com.google.android.material.snackbar.Snackbar

class RegistrationFour : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationFourBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        clickTextView()

        binding.imageButtonArrowBack.setOnClickListener {
            //Исправить передачу данных. Заменить переменные name, date, gender
            startActivity(
                RegistrationThree.newIntent(
                    this@RegistrationFour,
                    name = "",
                    date = "",
                    gender = false,
                )
            )
        }
        binding.buttonCreateAccount.setOnClickListener {
            Toast.makeText(this@RegistrationFour, "Create account!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Кликабельный текст для согласия на обработку данных
     */
    private fun clickTextView() {
        val fullText = getString(R.string.by_creating)
        val policy = getString(R.string.privacy_policy)
        val terms = getString(R.string.terms_of_payment)
        val spannableString = SpannableString(fullText)

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
    }

    companion object {

        private const val EXTRA_NAME = "name"
        private const val EXTRA_DATE = "date"
        private const val EXTRA_GENDER = "gender"
        private const val EXTRA_WEIGHT = "weight"
        private const val EXTRA_HEIGHT = "height"
        private const val EXTRA_TARGET_WEIGHT = "target weight"
        fun newIntent(
            context: Context,
            name: String,
            date: String,
            gender: Boolean,
            height: String,
            weight: String,
            targetWeight: String
        ): Intent {
            val intent = Intent(context, RegistrationTwo::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            intent.putExtra(EXTRA_WEIGHT, weight)
            intent.putExtra(EXTRA_HEIGHT, height)
            intent.putExtra(EXTRA_TARGET_WEIGHT, targetWeight)
            return intent
        }
    }
}