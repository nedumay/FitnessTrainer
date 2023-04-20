package com.example.testproject.app.presentation.registration.four

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.databinding.ActivityRegistrationFourBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class RegistrationFour : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrationFourBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RegistrationFourViewModel

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegistrationFourViewModel::class.java]

        val name = intent.getStringExtra(EXTRA_NAME)
        val lastName = intent.getStringExtra(EXTRA_LAST_NAME)
        val date = intent.getStringExtra(EXTRA_DATE)
        val gender = intent.getBooleanExtra(EXTRA_GENDER, false)
        val height = intent.getStringExtra(EXTRA_HEIGHT)
        val weight = intent.getStringExtra(EXTRA_WEIGHT)
        val targetWeight = intent.getStringExtra(EXTRA_TARGET_WEIGHT)
        Log.d(
            "RegistrationActivity",
            "Four activity get: $name, $date, $gender, $height, $weight, $targetWeight"
        )
        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(
                RegistrationThree.newIntent(
                    this@RegistrationFour,
                    name = name!!,
                    lastName = lastName!!,
                    date = date!!,
                    gender = false,
                )
            )
        }
        binding.buttonCreateAccount.setOnClickListener {
            val email = binding.editTextEmailRegistration.text?.trim().toString()
            val password = binding.editTextPasswordRegistration.text?.trim().toString()
            viewModel.signUp(
                email = email,
                password = password,
                name = name!!,
                lastName = lastName!!,
                date = date!!,
                gender = gender,
                height = height!!,
                weight = weight!!,
                targetWeight = targetWeight!!
            )
            viewModel.error.observe(this){
                Toast.makeText(this@RegistrationFour, it, Toast.LENGTH_LONG).show()
            }
            startActivity(LoginActivity.newIntent(this@RegistrationFour))

        }
        clickTextView()
        isEmailValid()
    }

    private fun isEmailValid() {
        binding.editTextEmailRegistration.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               val valid =
                   android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if(!valid){
                    binding.tilEmailRegistration.error = INVALID_ADDRESS
                } else{
                    binding.tilEmailRegistration.error = EMPTY_FIELD
                }
            }
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0){
                    binding.tilEmailRegistration.error = NOT_BE_EMPTY
                }
            }

        })
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
        private const val EXTRA_LAST_NAME = "last_name"
        private const val EXTRA_DATE = "date"
        private const val EXTRA_GENDER = "gender"
        private const val EXTRA_WEIGHT = "weight"
        private const val EXTRA_HEIGHT = "height"
        private const val EXTRA_TARGET_WEIGHT = "target weight"

        private const val EMPTY_FIELD = ""
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val NOT_BE_EMPTY = "Required Field!"
        fun newIntent(
            context: Context,
            name: String,
            lastName: String,
            date: String,
            gender: Boolean,
            height: String,
            weight: String,
            targetWeight: String
        ): Intent {
            val intent = Intent(context, RegistrationFour::class.java)
            intent.putExtra(EXTRA_NAME, name)
            intent.putExtra(EXTRA_LAST_NAME, lastName)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_GENDER, gender)
            intent.putExtra(EXTRA_WEIGHT, weight)
            intent.putExtra(EXTRA_HEIGHT, height)
            intent.putExtra(EXTRA_TARGET_WEIGHT, targetWeight)
            return intent
        }
    }
}