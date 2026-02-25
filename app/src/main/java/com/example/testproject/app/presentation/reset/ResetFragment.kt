package com.example.testproject.app.presentation.reset

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.EMPTY_FIELD_KEY
import com.example.testproject.app.common.INVALID_ADDRESS_KEY
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.FragmentResetBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class ResetFragment : Fragment() {

    private var _binding: FragmentResetBinding? = null
    private val binding: FragmentResetBinding
        get() = _binding ?: throw RuntimeException("FragmentResetBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ResetViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@ResetFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonArrowBack.setOnClickListener {
            launchLoginFragment()
        }

        binding.buttonSend.isEnabled = false
        enabledButton()

        binding.buttonSend.setOnClickListener {
            val email = binding.editTextEmailLogin.text?.trim().toString()
            observeViewModel()
            viewModel.resetPassword(email)
        }

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchLoginFragment()
                }
            })
    }

    private fun launchLoginFragment() {
        findNavController().navigate(R.id.action_resetFragment_to_loginFragment)
    }

    private fun observeViewModel() {
        val progressDialog = ProgressDialog(requireContext())
        val alertDialog = AlertDialog.Builder(requireContext())
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

    // Вынести в отдельную маску!!!
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
                    binding.tilEmailReset.error = INVALID_ADDRESS_KEY
                } else {
                    binding.tilEmailReset.error = EMPTY_FIELD_KEY
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "ResetFragment"
    }

}