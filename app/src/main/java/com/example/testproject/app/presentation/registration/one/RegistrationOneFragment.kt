package com.example.testproject.app.presentation.registration.one

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
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.DATE_OF_BIRTH_KEY
import com.example.testproject.app.common.LAST_NAME_KEY
import com.example.testproject.app.common.NAME_KEY
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.utils.DataMask
import com.example.testproject.databinding.FragmentRegistrationOneBinding

class RegistrationOneFragment : Fragment() {

    private var _binding: FragmentRegistrationOneBinding? = null
    private val binding: FragmentRegistrationOneBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationOneBinding == null")

    private val dataMask by lazy { DataMask() }

    private var getName: String? = null
    private var getLastName: String? = null
    private var getDateOfBirth: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationOneFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            getName = it.getString(NAME_KEY) ?: ""
            getLastName = it.getString(LAST_NAME_KEY) ?: ""
            getDateOfBirth = it.getString(DATE_OF_BIRTH_KEY) ?: ""
        }
        _binding = FragmentRegistrationOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultInitEditView()
        binding.editDateOfBirth.addTextChangedListener(dataMask)

    }

    override fun onResume() {
        super.onResume()

        enabledButton()
        binding.buttonNextRegistration.setOnClickListener {
            val name = binding.editTextNameRegistration.text?.trim().toString()
            val lastName = binding.editTextLastNameRegistration.text?.trim().toString()
            val dateOfBirth = binding.editDateOfBirth.text?.trim().toString()
            Log.d("RegistrationOneFragment", "One activity extra: $name, $lastName, $dateOfBirth")
            launchTwoFragment(name, lastName, dateOfBirth)
        }

        binding.imageButtonArrowBack.setOnClickListener {
            launchLoginFragment()
        }

        onBackFragment()
    }

    private fun defaultInitEditView() {
        binding.editTextNameRegistration.setText(getName)
        binding.editTextLastNameRegistration.setText(getLastName)
        binding.editDateOfBirth.setText(getDateOfBirth)
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
        findNavController().navigate(R.id.action_registrationOneFragment_to_loginFragment)
    }

    private fun launchTwoFragment(name: String, lastName: String, dateOfBirth: String) {
        val bundle = Bundle()
        bundle.putString(NAME_KEY, name)
        bundle.putString(LAST_NAME_KEY, lastName)
        bundle.putString(DATE_OF_BIRTH_KEY, dateOfBirth)
        findNavController().navigate(
            R.id.action_registrationOneFragment_to_registrationTwoFragment,
            bundle
        )
    }


    private fun enabledButton() {
        if (getName?.trim()?.isNotEmpty() == true && getLastName?.trim()
                ?.isNotEmpty() == true && getDateOfBirth?.trim()?.length == 10
        ) {
            binding.buttonNextRegistration.isEnabled = true
        } else {

            binding.editTextNameRegistration.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editTextNameRegistration.text?.length ?: 0) > 0
                                && (binding.editTextLastNameRegistration.text?.length ?: 0) > 0
                                && (binding.editDateOfBirth.text?.length ?: 0) == 10)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            binding.editTextLastNameRegistration.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    Log.d("RegistrationOneFragment", "afterTextChanged")
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editTextLastNameRegistration.text?.length ?: 0) > 0
                                && (binding.editTextNameRegistration.text?.length ?: 0) > 0
                                && (binding.editDateOfBirth.text?.length ?: 0) == 10)
                }
            })

            binding.editDateOfBirth.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editDateOfBirth.text?.length ?: 0) == 10
                                && (binding.editTextNameRegistration.text?.length ?: 0) > 0
                                && (binding.editTextLastNameRegistration.text?.length ?: 0) > 0)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "RegistrationOneFragment"
    }


}