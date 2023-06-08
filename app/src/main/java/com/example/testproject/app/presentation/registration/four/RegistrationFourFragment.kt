package com.example.testproject.app.presentation.registration.four

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.utils.EmailMask
import com.example.testproject.databinding.FragmentRegistrationFourBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class RegistrationFourFragment : Fragment() {

    private var _binding: FragmentRegistrationFourBinding? = null
    private val binding: FragmentRegistrationFourBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationFourBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: RegistrationFourViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegistrationFourViewModel::class.java]
    }

    private val emailMask by lazy {
        EmailMask(binding.tilEmailRegistration)
    }

    private var name: String? = null
    private var lastName: String? = null
    private var date: String? = null
    private var gender: Boolean? = null
    private var height: String? = null
    private var weight: String? = null
    private var targetWeight: String? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@RegistrationFourFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(GET_NAME_KEY)
            lastName = it.getString(GET_LAST_NAME_KEY)
            date = it.getString(GET_DATE_OF_BIRTH_KEY)
            gender = it.getBoolean(GET_GENDER_KEY)
            height = it.getString(GET_HEIGHT_KEY)
            weight = it.getString(GET_WEIGHT_KEY)
            targetWeight = it.getString(GET_TARGET_WEIGHT_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationFourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (name == null || lastName == null || date == null || gender == null || height == null || weight == null || targetWeight == null) {
            launchRegistrationThreeFragment()
        } else {
            Log.d(
                "RegistrationActivity",
                "Four activity get: $name, $date, $gender, $height, $weight, $targetWeight"
            )
            createAccount()
        }

        binding.editTextEmailRegistration.addTextChangedListener(emailMask)

        binding.imageButtonArrowBack.setOnClickListener {
            launchRegistrationThreeFragment()
        }

        clickTextView()

        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchRegistrationThreeFragment()
                }
            })
    }

    private fun createAccount() {
        binding.buttonCreateAccount.setOnClickListener {
            val email = binding.editTextEmailRegistration.text?.trim().toString()
            val password = binding.editTextPasswordRegistration.text?.trim().toString()

            viewModel.signUp(
                email = email,
                password = password,
                name = name!!,
                lastName = lastName!!,
                date = date!!,
                gender = gender!!,
                height = height!!,
                weight = weight!!,
                targetWeight = targetWeight!!
            )
            viewModel.error.onEach {
                progressDialog = ProgressDialog(requireContext())
                val alertDialog = AlertDialog.Builder(requireContext())
                when (it) {
                    is Resource.Loading -> {
                        Log.d("RegistrationActivity", "Loading: $it")
                        progressDialog.setTitle(R.string.create_acc_alert)
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
                            launchLoginFragment()
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
    }

    private fun launchLoginFragment() {
        findNavController().navigate(R.id.action_registrationFourFragment_to_loginFragment)
    }

    private fun launchRegistrationThreeFragment() {
        findNavController().navigate(R.id.action_registrationFourFragment_to_registrationThreeFragment)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val GET_NAME_KEY = "name"
        private const val GET_LAST_NAME_KEY = "lastName"
        private const val GET_DATE_OF_BIRTH_KEY = "dateOfBirth"
        private const val GET_GENDER_KEY = "gender"
        private const val GET_HEIGHT_KEY = "height"
        private const val GET_WEIGHT_KEY = "weight"
        private const val GET_TARGET_WEIGHT_KEY = "targetWeight"
    }

}