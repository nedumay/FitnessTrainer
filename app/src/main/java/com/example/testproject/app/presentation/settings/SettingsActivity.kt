package com.example.testproject.app.presentation.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.ActivitySettingsBinding
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
        Log.d("Settings activity", "Extra current user id: $currentUserId")

        if (currentUserId != null) {
            viewModel.loadDataForUser(currentUserId!!)
        }
        viewModel.firebaseUser.observe(this){
            Log.d("Settings activity", "User observe: $it")
            binding.textName.text = "${it.name} ${it.lastName}"
            binding.textNameUser.text = it.name
            binding.textLastNameUser.text = it.lastName
            binding.textDate.text = it.dateOfBirth
            binding.textCity.text = it.city
            binding.textCountry.text = it.country
            binding.textEmail.text = it.email
            if(it.gender){
                binding.imageViewUser.setImageResource(R.drawable.avatar_male)
            } else{
                binding.imageViewUser.setImageResource(R.drawable.avatar_female)
            }
        }

    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "current_id"

        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, SettingsActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            return intent
        }
    }
}