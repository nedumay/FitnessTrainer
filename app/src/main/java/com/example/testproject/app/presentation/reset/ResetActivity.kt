package com.example.testproject.app.presentation.reset

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.utils.EmailMask
import com.example.testproject.databinding.ActivityResetBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ResetActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResetBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ResetViewModel

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[ResetViewModel::class.java]

        binding.imageButtonArrowBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.buttonSend.isEnabled = false
        enabledButton()
        binding.buttonSend.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            observeViewModel()
            viewModel.resetPassword(email)
        }
    }

    private fun observeViewModel() {
        val progressDialog = ProgressDialog(this@ResetActivity)
        val alertDialog = AlertDialog.Builder(this@ResetActivity)
        viewModel.error.onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("ResetActivity", "Loading: $it")
                    progressDialog.setTitle(R.string.check_account)
                    progressDialog.isIndeterminate = true
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                }

                is Resource.Error -> {
                    Log.d("ResetActivity", "Error: $it")
                    progressDialog.dismiss()
                    alertDialog.setTitle(R.string.error)
                    alertDialog.setIcon(R.drawable.ic_error)
                    alertDialog.setMessage("${it.message}")
                    alertDialog.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }

                is Resource.Success -> {
                    Log.d("ResetActivity", "Success: $it")
                    progressDialog.dismiss()
                    alertDialog.setTitle(R.string.success)
                    alertDialog.setIcon(R.drawable.ic_check)
                    alertDialog.setMessage(R.string.suc_reset)
                    alertDialog.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonSend.isEnabled = (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val valid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if (!valid) {
                    binding.tilEmailReset.error = INVALID_ADDRESS
                } else {
                    binding.tilEmailReset.error = EMPTY_FIELD
                }
            }
        })
    }

    companion object {
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val EMPTY_FIELD = ""
        fun newIntent(context: Context): Intent {
            return Intent(context, ResetActivity::class.java)
        }
    }
}