package com.example.testproject.app.presentation.settings

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]
    }

    private lateinit var currentUserId: String
    private lateinit var userIdSharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@SettingsFragment)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем id пользователя
        userIdSharedPreferences = requireActivity()
            .getSharedPreferences(
                USER_SHARED_PREF,
                AppCompatActivity.MODE_PRIVATE
            )
        currentUserId = userIdSharedPreferences.getString(USER_ID, null) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alertDialog = AlertDialog.Builder(requireContext())

        if (currentUserId.isEmpty()) {
            launchLoginFragment()
        } else {
            viewModel.loadDataForUser(currentUserId)
        }

        userInfoObserver()
        //Выход из аккаунта
        logOutAccount(alertDialog)
        // Удаление аккаунта
        deleteAccount(alertDialog)
        // Выход из настроек
        binding.textViewBack.setOnClickListener {
            launchDashboardFragment()
        }
        onBackFragment()
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchDashboardFragment()
                }
            })
    }

    private fun deleteAccount(alertDialog: AlertDialog.Builder) {
        binding.textViewDeleteProfile.setOnClickListener {
            alertDialog.setTitle(R.string.warning)
            alertDialog.setIcon(R.drawable.ic_warning)
            alertDialog.setMessage(R.string.war_delete)
            alertDialog.setPositiveButton(R.string.delete) { dialog, which ->
                userIdSharedPreferences.edit().clear().apply()
                dialog.dismiss()
                viewModel.deleteUser(currentUserId)
                launchLoginFragment()
            }
            alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun logOutAccount(alertDialog: AlertDialog.Builder) {
        binding.buttonOutput.setOnClickListener {
            alertDialog.setTitle(R.string.warning)
            alertDialog.setIcon(R.drawable.ic_warning)
            alertDialog.setMessage(R.string.war_log_out)
            alertDialog.setPositiveButton(R.string.output) { dialog, which ->
                userIdSharedPreferences.edit().clear().apply()
                dialog.dismiss()
                viewModel.signOut()
                launchLoginFragment()
            }
            alertDialog.setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun userInfoObserver() {
        viewModel.userInfo.onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("Account user", "Loading: $it")
                    binding.constraintLayout.visibility = View.GONE
                    binding.materialCardView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    Log.d("Account user", "Error: $it")
                    binding.constraintLayout.visibility = View.GONE
                    binding.materialCardView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    userIdSharedPreferences.edit().clear().apply()
                    viewModel.signOut()
                    launchLoginFragment()
                }

                is Resource.Success -> {
                    Log.d("Account user", "Success: $it")
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.materialCardView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.textName.text = "${it.data.name} ${it.data.lastName}"
                    binding.textNameUser.text = it.data.name
                    binding.textLastNameUser.text = it.data.lastName
                    binding.textDate.text = it.data.dateOfBirth
                    binding.textWeight.text = it.data.weight
                    binding.textHeight.text = it.data.height
                    binding.textEmail.text = it.data.email
                    if (it.data.gender) {
                        binding.imageViewUser.setImageResource(R.drawable.avatar_male)
                    } else {
                        binding.imageViewUser.setImageResource(R.drawable.avatar_female)
                    }
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun launchLoginFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
    }

    private fun launchDashboardFragment() {
        findNavController().navigate(R.id.action_settingsFragment_to_dashboardFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val USER_SHARED_PREF = "userPreferences"
        private const val USER_ID = "userId"
    }

}