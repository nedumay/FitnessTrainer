package com.example.testproject.app.presentation.registration.three

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.databinding.FragmentRegistrationThreeBinding


class RegistrationThreeFragment : Fragment() {

    private var _binding: FragmentRegistrationThreeBinding? = null
    private val binding: FragmentRegistrationThreeBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationThreeBinding == null")

    private var name: String? = null
    private var lastName: String? = null
    private var date: String? = null
    private var gender: Boolean? = null
    private var sufWeight: String = ""
    private var sufHeight: String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationThreeFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sufWeight = resources.getString(R.string.ft)
        sufHeight = resources.getString(R.string.lbs)
        arguments?.let {
            name = it.getString(GET_NAME_KEY)
            lastName = it.getString(GET_LAST_NAME_KEY)
            date = it.getString(GET_DATE_OF_BIRTH_KEY)
            gender = it.getBoolean(GET_GENDER_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (name == null || lastName == null || date == null || gender == null) {
            launchRegistrationTwoFragment()
        } else {
            Log.d("RegistrationActivity", "Three activity get: $name, $lastName, $date, $gender")
            switchButton()

            binding.buttonNextRegistration.setOnClickListener {
                val height = binding.editTextHeight.text?.trim().toString() + sufHeight
                val weight = binding.editTextWeight.text?.trim().toString() + sufWeight
                val targetWeight = binding.editTextTarget.text?.trim().toString() + sufWeight
                launchRegistrationFourFragment(
                    height,
                    weight,
                    targetWeight,
                    name,
                    lastName,
                    date,
                    gender!!
                )
            }

            binding.imageButtonArrowBack.setOnClickListener {
                launchRegistrationTwoFragment()
            }

        }
    }

    private fun switchButton() {
        binding.switchBtn.setOnCheckedChangeListener { _, checkedId ->
            requireActivity().findViewById<RadioButton>(checkedId)?.apply {
                if (checkedId == R.id.lbsRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.lbs)
                    binding.tilWeight.suffixText = resources.getString(R.string.ft)
                    binding.tilTarget.suffixText = resources.getString(R.string.ft)
                    sufWeight = resources.getString(R.string.ft)
                    sufHeight = resources.getString(R.string.lbs)
                } else if (checkedId == R.id.kgRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.sm)
                    binding.tilWeight.suffixText = resources.getString(R.string.kg)
                    binding.tilTarget.suffixText = resources.getString(R.string.kg)
                    sufWeight = resources.getString(R.string.kg)
                    sufHeight = resources.getString(R.string.sm)
                }
            }
        }
    }

    private fun launchRegistrationTwoFragment() {
        findNavController().navigate(R.id.action_registrationThreeFragment_to_registrationTwoFragment)
    }

    private fun launchRegistrationFourFragment(
        height: String,
        weight: String,
        targetWeight: String,
        name: String?,
        lastName: String?,
        date: String?,
        gender: Boolean
    ) {
        val bundle = Bundle()
        bundle.putString(GET_NAME_KEY, name)
        bundle.putString(GET_LAST_NAME_KEY, lastName)
        bundle.putString(GET_DATE_OF_BIRTH_KEY, date)
        bundle.putBoolean(GET_GENDER_KEY, gender)
        bundle.putString(PUT_HEIGHT_KEY, height)
        bundle.putString(PUT_WEIGHT_KEY, weight)
        bundle.putString(PUT_TARGET_WEIGHT_KEY, targetWeight)
        findNavController().navigate(
            R.id.action_registrationThreeFragment_to_registrationFourFragment,
            bundle
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GET_NAME_KEY = "name"
        private const val GET_LAST_NAME_KEY = "lastName"
        private const val GET_DATE_OF_BIRTH_KEY = "dateOfBirth"
        private const val GET_GENDER_KEY = "gender"
        private const val PUT_HEIGHT_KEY = "height"
        private const val PUT_WEIGHT_KEY = "weight"
        private const val PUT_TARGET_WEIGHT_KEY = "targetWeight"
    }

}