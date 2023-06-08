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
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.utils.DataMask
import com.example.testproject.databinding.FragmentRegistrationOneBinding

class RegistrationOneFragment : Fragment() {

    private var _binding: FragmentRegistrationOneBinding? = null
    private val binding: FragmentRegistrationOneBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationOneBinding == null")

    private val dataMask = DataMask()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationOneFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editDateOfBirth.addTextChangedListener(dataMask)
        binding.buttonNextRegistration.isEnabled = false

        enabledButton()

        binding.buttonNextRegistration.setOnClickListener {
            val name = binding.editTextNameRegistration.text?.trim().toString()
            val lastName = binding.editTextLastNameRegistration.text?.trim().toString()
            val date = binding.editDateOfBirth.text?.trim().toString()
            Log.d("RegistrationActivity", "One activity extra: $name, $lastName, $date ")
            launchTwoFragment(name, lastName, date)
        }
        binding.imageButtonArrowBack.setOnClickListener {
            launchLoginFragment()
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
        findNavController().navigate(R.id.action_registrationOneFragment_to_loginFragment)
    }

    private fun launchTwoFragment(name: String, lastName: String, date: String) {
        val bundle = Bundle()
        bundle.putString(PUT_NAME_KEY, name)
        bundle.putString(PUT_LAST_NAME_KEY, lastName)
        bundle.putString(PUT_DATE_OF_BIRTH_KEY, date)
        findNavController().navigate(R.id.action_registrationOneFragment_to_registrationTwoFragment, bundle)
    }


    private fun enabledButton() {
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PUT_NAME_KEY = "name"
        private const val PUT_LAST_NAME_KEY = "lastName"
        private const val PUT_DATE_OF_BIRTH_KEY = "dateOfBirth"
    }


}