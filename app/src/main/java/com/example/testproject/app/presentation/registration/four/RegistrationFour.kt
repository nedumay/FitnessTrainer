package com.example.testproject.app.presentation.registration.four

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.app.utils.EmailMask
import com.example.testproject.databinding.ActivityRegistrationFourBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val emailMask by lazy {
        EmailMask(binding.tilEmailRegistration)
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
        binding.editTextEmailRegistration.addTextChangedListener(emailMask)
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
            finish()
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
            viewModel.error.onEach {
                val progressDialog = ProgressDialog(this@RegistrationFour)
                val alertDialog = AlertDialog.Builder(this@RegistrationFour)
                when (it) {
                    is Resource.Loading -> {
                        Log.d("RegistrationActivity", "Loading: $it")
                        progressDialog.setTitle(R.string.create_acc_alert)
                        progressDialog.isIndeterminate = true
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                    }

                    is Resource.Success -> {
                        Log.d("RegistrationActivity", "Success: $it")
                        progressDialog.dismiss()
                        alertDialog.setTitle(R.string.success)
                        alertDialog.setIcon(R.drawable.ic_check)
                        alertDialog.setMessage(R.string.create_suc_alert)
                        alertDialog.setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                            startActivity(LoginActivity.newIntent(this@RegistrationFour))
                            finish()
                        }
                        alertDialog.show()
                    }

                    is Resource.Error -> {
                        Log.d("RegistrationActivity", "Error: $it")
                        progressDialog.dismiss()
                        alertDialog.setTitle(R.string.error)
                        alertDialog.setIcon(R.drawable.ic_error)
                        alertDialog.setMessage("${it.message}")
                        alertDialog.setPositiveButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }
                }
            }.launchIn(lifecycleScope)
        }
        clickTextView()

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