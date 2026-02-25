package com.example.testproject.app.presentation.registration.two

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.DATE_OF_BIRTH_KEY
import com.example.testproject.app.common.GENDER_KEY
import com.example.testproject.app.common.LAST_NAME_KEY
import com.example.testproject.app.common.NAME_KEY
import com.example.testproject.app.presentation.app.App
import com.example.testproject.databinding.FragmentRegistrationTwoBinding


class RegistrationTwoFragment : Fragment() {

    private var _binding: FragmentRegistrationTwoBinding? = null
    private val binding: FragmentRegistrationTwoBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationTwoBinding == null")

    private var name: String? = null
    private var lastName: String? = null
    private var date: String? = null
    private var gender: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationTwoFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_KEY)
            lastName = it.getString(LAST_NAME_KEY)
            date = it.getString(DATE_OF_BIRTH_KEY)
            gender = it.getBoolean(GENDER_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNextRegistration.isEnabled = false

        if (name == null || lastName == null || date == null) {
            launchRegistrationOneFragment(name, lastName, date)
            return
        } else {
            Log.d("RegistrationActivity", "Two activity get: $name, $lastName, $date, $gender")
            defaultGenderSet(gender)
        }
    }

    override fun onResume() {
        super.onResume()
        genderObserver()
        binding.imageButtonArrowBack.setOnClickListener {
            launchRegistrationOneFragment(name, lastName, date)
        }

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchRegistrationOneFragment(name, lastName, date)
                }
            })
    }

    private fun defaultGenderSet(gender: Boolean) {
        if (gender) {
            binding.cardViewMale.isChecked = true
            binding.cardViewFmale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
        } else {
            binding.cardViewFmale.isChecked = true
            binding.cardViewMale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
        }
    }

    private fun genderObserver() {
        binding.cardViewMale.setOnClickListener {
            binding.cardViewMale.isChecked = true
            binding.cardViewFmale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
            gender = true
        }

        binding.cardViewFmale.setOnClickListener {
            binding.cardViewFmale.isChecked = true
            binding.cardViewMale.isChecked = false
            binding.buttonNextRegistration.isEnabled = true
            gender = false
        }

        binding.buttonNextRegistration.setOnClickListener {
            launchThreeFragment(name, lastName, date, gender)
        }

    }

    private fun launchThreeFragment(
        name: String?,
        lastName: String?,
        date: String?,
        gender: Boolean
    ) {
        val bundle = Bundle()
        bundle.putString(NAME_KEY, name)
        bundle.putString(LAST_NAME_KEY, lastName)
        bundle.putString(DATE_OF_BIRTH_KEY, date)
        bundle.putBoolean(GENDER_KEY, gender)
        findNavController().navigate(
            R.id.action_registrationTwoFragment_to_registrationThreeFragment,
            bundle
        )
    }

    private fun launchRegistrationOneFragment(name: String?, lastName: String?, date: String?) {
        val bundle = Bundle()
        bundle.putString(NAME_KEY, name)
        bundle.putString(LAST_NAME_KEY, lastName)
        bundle.putString(DATE_OF_BIRTH_KEY, date)
        findNavController().navigate(
            R.id.action_registrationTwoFragment_to_registrationOneFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "RegistrationTwoFragment"
    }


}