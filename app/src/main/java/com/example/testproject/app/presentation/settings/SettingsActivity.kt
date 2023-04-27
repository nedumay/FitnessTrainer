package com.example.testproject.app.presentation.settings

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.databinding.ActivitySettingsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SettingsViewModel

    private var currentUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        if (!intent.hasExtra(EXTRA_CURRENT_USER_ID)) {
            finish()
            return
        } else {
            currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID)
        }
        Log.d("Account user", "Extra current user id settings: $currentUserId")

        if (currentUserId != null) {
            viewModel.loadDataForUser(currentUserId!!)
        }
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
                    Toast.makeText(this@SettingsActivity, it.message, Toast.LENGTH_SHORT).show()
                    finish()
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
                    binding.textWeight.text = it.data.weight + " kg" // исправить отображение
                    binding.textHeight.text = it.data.height + " sm" // исправить отображение
                    binding.textEmail.text = it.data.email
                    if (it.data.gender) {
                        binding.imageViewUser.setImageResource(R.drawable.avatar_male)
                    } else {
                        binding.imageViewUser.setImageResource(R.drawable.avatar_female)
                    }
                }
            }
        }.launchIn(lifecycleScope)

        val alertDialog = AlertDialog.Builder(this@SettingsActivity)
        binding.buttonOutput.setOnClickListener {
            alertDialog.setTitle("Warning")
            alertDialog.setIcon(R.drawable.ic_warning)
            alertDialog.setMessage("Do you really want to log out?")
            alertDialog.setPositiveButton("Log out") { dialog, which ->
                dialog.dismiss()
                viewModel.signOut()
                startActivity(LoginActivity.newIntent(this@SettingsActivity))
                finish()
            }
            alertDialog.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }

        binding.textViewDeleteProfile.setOnClickListener {
            alertDialog.setTitle("Warning")
            alertDialog.setIcon(R.drawable.ic_warning)
            alertDialog.setMessage("Are you sure you want to delete your profile?")
            alertDialog.setPositiveButton("Delete") { dialog, which ->
                dialog.dismiss()
                viewModel.deleteUser(currentUserId!!)
                startActivity(LoginActivity.newIntent(this@SettingsActivity))
                finish()
            }
            alertDialog.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
        binding.textViewBack.setOnClickListener {
            finish()
        }

    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "current_id"

        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, SettingsActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }
}