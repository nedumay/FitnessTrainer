package com.example.testproject.app.presentation.registration.three

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
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
        sufWeight = resources.getString(R.string.lbs)
        sufHeight = resources.getString(R.string.ft)
        arguments?.let {
            name = it.getString(PUT_GET_NAME_KEY)
            lastName = it.getString(PUT_GET_LAST_NAME_KEY)
            date = it.getString(PUT_GET_DATE_OF_BIRTH_KEY)
            gender = it.getBoolean(PUT_GET_GENDER_KEY)
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
            launchRegistrationTwoFragment(name, lastName, date, gender)
        }

    }

    override fun onResume() {
        super.onResume()

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
                gender ?: false
            )
        }

        binding.imageButtonArrowBack.setOnClickListener {
            launchRegistrationTwoFragment(name, lastName, date, gender)
        }

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchRegistrationTwoFragment(name, lastName, date, gender)
                }
            })
    }

    private fun switchButton() {
        binding.switchBtn.setOnCheckedChangeListener { _, checkedId ->
            requireActivity().findViewById<RadioButton>(checkedId)?.apply {
                if (checkedId == R.id.lbsRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.ft)
                    binding.tilWeight.suffixText = resources.getString(R.string.lbs)
                    binding.tilTarget.suffixText = resources.getString(R.string.lbs)
                    sufWeight = resources.getString(R.string.lbs)
                    sufHeight = resources.getString(R.string.ft)
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

    private fun launchRegistrationTwoFragment(name: String?, lastName: String?, date: String?, gender: Boolean?) {
        val bundle = Bundle()
        bundle.putString(PUT_GET_NAME_KEY, name)
        bundle.putString(PUT_GET_LAST_NAME_KEY, lastName)
        bundle.putString(PUT_GET_DATE_OF_BIRTH_KEY, date)
        bundle.putBoolean(PUT_GET_GENDER_KEY, gender ?: false)
        findNavController().navigate(R.id.action_registrationThreeFragment_to_registrationTwoFragment, bundle)
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
        bundle.putString(PUT_GET_NAME_KEY, name)
        bundle.putString(PUT_GET_LAST_NAME_KEY, lastName)
        bundle.putString(PUT_GET_DATE_OF_BIRTH_KEY, date)
        bundle.putBoolean(PUT_GET_GENDER_KEY, gender)
        bundle.putString(PUT_GET_HEIGHT_KEY, height)
        bundle.putString(PUT_GET_WEIGHT_KEY, weight)
        bundle.putString(PUT_GET_TARGET_WEIGHT_KEY, targetWeight)
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
        private const val PUT_GET_NAME_KEY = "name"
        private const val PUT_GET_LAST_NAME_KEY = "lastName"
        private const val PUT_GET_DATE_OF_BIRTH_KEY = "dateOfBirth"
        private const val PUT_GET_GENDER_KEY = "gender"
        private const val PUT_GET_HEIGHT_KEY = "height"
        private const val PUT_GET_WEIGHT_KEY = "weight"
        private const val PUT_GET_TARGET_WEIGHT_KEY = "targetWeight"
    }

}