package com.example.testproject.app.presentation.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@LoginFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val userIdSharedPreferences = requireActivity()
            .getSharedPreferences(
                USER_SHARED_PREF,
                AppCompatActivity.MODE_PRIVATE
            )
        val sharedEditor = userIdSharedPreferences.edit()
        saveUser(userIdSharedPreferences)

        binding.buttonEnterLogin.isEnabled = false
        enabledButton()

        binding.textViewForgotPassword.setOnClickListener {
            launchResetFragment()
        }

        binding.buttonRegistrationLogin.setOnClickListener {
            launchRegistrationOneFragment()
        }

        binding.buttonEnterLogin.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            val password = binding.editTextPasswordLogin.text?.trim().toString()
            viewModel.login(email, password)
            observeViewModel(sharedEditor)
        }
    }

    private fun launchRegistrationOneFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationOneFragment)
    }

    private fun launchResetFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_resetFragment)
    }

    private fun launchDashboardFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
    }


    private fun saveUser(userIdSharedPref: SharedPreferences) {
        //Если id сохранен в SharedPreferences то заходим в приложение
        if (userIdSharedPref.contains(USER_ID)) {
            val data = userIdSharedPref.getString(USER_ID, "") ?: ""
            if(data.isNotEmpty()){
                launchDashboardFragment()
            }

        }
    }

    private fun observeViewModel(sharedEditor: SharedPreferences.Editor) {
        val progressDialog = ProgressDialog(requireContext())
        val alertDialog = AlertDialog.Builder(requireContext())
        viewModel.firebaseUser.onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("LoginActivityData", "Loading: $it")
                    progressDialog.setTitle(R.string.login_alert)
                    progressDialog.isIndeterminate = true
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }

                is Resource.Success -> {
                    Log.d("LoginActivityData", "Success: ${it.data}")
                    // Сохраняем id пользователя и заходим в приложение
                    sharedEditor.putString(USER_ID, it.data).apply()
                    progressDialog.dismiss()
                    launchDashboardFragment()
                }

                is Resource.Error -> {
                    Log.d("LoginActivityData", "Error: $it")
                    progressDialog.dismiss()
                    alertDialog.setTitle(R.string.error)
                    alertDialog.setIcon(R.drawable.ic_error)
                    alertDialog.setMessage(it.message)
                    alertDialog.setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    alertDialog.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    // Вынести в отдельную маску
    private fun enabledButton() {
        binding.editTextEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextEmailLogin.text?.length
                    ?: 0) > 0 && (binding.editTextPasswordLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val valid =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(s?.trim().toString()).matches()
                if (!valid) {
                    binding.tilEmailLogin.error = INVALID_ADDRESS
                } else {
                    binding.tilEmailLogin.error = EMPTY_FIELD
                }
            }
        })
        binding.editTextPasswordLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                binding.buttonEnterLogin.isEnabled = (binding.editTextPasswordLogin.text?.length
                    ?: 0) > 0 && (binding.editTextEmailLogin.text?.length ?: 0) > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val INVALID_ADDRESS = "Invalid Email address"
        private const val EMPTY_FIELD = ""
        private const val USER_SHARED_PREF = "userPreferences"
        private const val USER_ID = "userId"
    }
}