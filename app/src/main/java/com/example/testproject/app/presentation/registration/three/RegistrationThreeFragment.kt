package com.example.testproject.app.presentation.registration.three

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlin.math.floor


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

    private var height: String = ""
    private var weight: String = ""
    private var targetWeight: String = ""

    private var checkIdSave: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationThreeFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(PUT_GET_NAME_KEY)
            lastName = it.getString(PUT_GET_LAST_NAME_KEY)
            date = it.getString(PUT_GET_DATE_OF_BIRTH_KEY)
            gender = it.getBoolean(PUT_GET_GENDER_KEY)
            checkIdSave = it.getInt(PUT_GET_CHECKED_ID_KEY)
            if(checkIdSave == R.id.kgRadioBtn){
                sufWeight = resources.getString(R.string.kg)
                sufHeight = resources.getString(R.string.sm)
            } else {
                sufWeight = resources.getString(R.string.lbs)
                sufHeight = resources.getString(R.string.ft)
            }
            height = it.getString(PUT_GET_HEIGHT_KEY)?.replace(sufHeight, "")  ?: ""
            weight = it.getString(PUT_GET_WEIGHT_KEY)?.replace(sufWeight, "") ?: ""
            targetWeight = it.getString(PUT_GET_TARGET_WEIGHT_KEY)?.replace(sufWeight, "") ?: ""
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
        defaultInitEditView()
    }

    private fun defaultInitEditView() {
        if(checkIdSave == R.id.kgRadioBtn){
            binding.switchBtn.check(R.id.kgRadioBtn)
            binding.tilHeight.suffixText = resources.getString(R.string.sm)
            binding.tilWeight.suffixText = resources.getString(R.string.kg)
            binding.tilTarget.suffixText = resources.getString(R.string.kg)
        } else {
            binding.switchBtn.check(R.id.lbsRadioBtn)
            binding.tilHeight.suffixText = resources.getString(R.string.ft)
            binding.tilWeight.suffixText = resources.getString(R.string.lbs)
            binding.tilTarget.suffixText = resources.getString(R.string.lbs)
        }
        binding.editTextHeight.setText(height)
        binding.editTextWeight.setText(weight)
        binding.editTextTarget.setText(targetWeight)
    }

    override fun onResume() {
        super.onResume()

        switchButton()
        enabledButton()
        binding.buttonNextRegistration.setOnClickListener {
            height = binding.editTextHeight.text?.trim().toString() + sufHeight
            weight = binding.editTextWeight.text?.trim().toString() + sufWeight
            targetWeight = binding.editTextTarget.text?.trim().toString() + sufWeight
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

    @SuppressLint("DefaultLocale")
    private fun switchButton() {
        binding.switchBtn.setOnCheckedChangeListener { _, checkedId ->
            requireActivity().findViewById<RadioButton>(checkedId)?.apply {
                if (checkedId == R.id.lbsRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.ft)
                    binding.tilWeight.suffixText = resources.getString(R.string.lbs)
                    binding.tilTarget.suffixText = resources.getString(R.string.lbs)
                    sufWeight = resources.getString(R.string.lbs)
                    sufHeight = resources.getString(R.string.ft)
                    if(binding.editTextHeight.text?.isNotEmpty() == true){
                        var h = binding.editTextHeight.text.toString().toFloat() / 30.48
                        h = floor(h * 100.0) / 100.0
                        binding.editTextHeight.setText(h.toString())
                    }
                    if(binding.editTextWeight.text?.isNotEmpty() == true){
                        var w = binding.editTextWeight.text.toString().toFloat() / 2.205
                        w = floor(w * 100.0) / 100.0
                        binding.editTextWeight.setText(w.toString())
                    }
                    if(binding.editTextTarget.text?.isNotEmpty() == true){
                        var t = binding.editTextTarget.text.toString().toFloat() / 2.205
                        t = floor(t * 100.0) / 100.0
                        binding.editTextTarget.setText(t.toString())
                    }
                    checkIdSave = R.id.lbsRadioBtn
                } else if (checkedId == R.id.kgRadioBtn) {
                    binding.tilHeight.suffixText = resources.getString(R.string.sm)
                    binding.tilWeight.suffixText = resources.getString(R.string.kg)
                    binding.tilTarget.suffixText = resources.getString(R.string.kg)
                    sufWeight = resources.getString(R.string.kg)
                    sufHeight = resources.getString(R.string.sm)
                    if(binding.editTextHeight.text?.isNotEmpty() == true){
                        var h = binding.editTextHeight.text.toString().toFloat() * 30.48
                        h = floor(h * 100.0) / 100.0
                        binding.editTextHeight.setText(h.toString())
                    }
                    if(binding.editTextWeight.text?.isNotEmpty() == true){
                        var w = binding.editTextWeight.text.toString().toFloat() * 2.205
                        w = floor(w * 100.0) / 100.0
                        binding.editTextWeight.setText(w.toString())
                    }
                    if(binding.editTextTarget.text?.isNotEmpty() == true){
                        var t = binding.editTextTarget.text.toString().toFloat() * 2.205
                        t = floor(t * 100.0) / 100.0
                        binding.editTextTarget.setText(t.toString())
                    }
                    checkIdSave = R.id.kgRadioBtn
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
        height: String?,
        weight: String?,
        targetWeight: String?,
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
        checkIdSave?.let { bundle.putInt(PUT_GET_CHECKED_ID_KEY, it) }
        findNavController().navigate(
            R.id.action_registrationThreeFragment_to_registrationFourFragment,
            bundle
        )

    }

    private fun enabledButton() {
        if (height.trim().isNotEmpty() && weight.trim().isNotEmpty() && targetWeight.trim().isNotEmpty()) {
            binding.buttonNextRegistration.isEnabled = true
        } else {

            binding.editTextHeight.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editTextHeight.text?.length ?: 0) > 0
                                && (binding.editTextWeight.text?.length ?: 0) > 0
                                && (binding.editTextTarget.text?.length ?: 0) > 0)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            binding.editTextWeight.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editTextWeight.text?.length ?: 0) > 0
                                && (binding.editTextHeight.text?.length ?: 0) > 0
                                && (binding.editTextTarget.text?.length ?: 0) > 0)
                }
            })

            binding.editTextTarget.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    binding.buttonNextRegistration.isEnabled =
                        ((binding.editTextTarget.text?.length ?: 0) > 0
                                && (binding.editTextHeight.text?.length ?: 0) > 0
                                && (binding.editTextWeight.text?.length ?: 0) > 0)
                }
            })
        }
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
        private const val PUT_GET_CHECKED_ID_KEY = "checkedId"
    }



}