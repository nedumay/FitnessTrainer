package com.example.testproject.app.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.notification.NotificationActivity
import com.example.testproject.app.presentation.settings.SettingsActivity
import com.example.testproject.databinding.ActivityDashboardBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DashboardViewModel
    private var currentUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]

        //val currentUserId: String?
        if (!intent.hasExtra(EXTRA_CURRENT_USER_ID)) {
            finish()
            return
        } else {
            currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID)
        }
        Log.d("Dashboard account user", "Extra current user id: $currentUserId")
        if (currentUserId != null) {
            viewModel.loadDataForUser(currentUserId!!)
        }
        viewModel.firebaseUser.observe(this){
            Log.d("Dashboard account user", "User observe: $it")
        }
        appBarMenu()

        binding.addScheduleButton.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, NotificationActivity::class.java))
        }
    }

    private fun appBarMenu() {

        binding.appBarLayout.setOnClickListener { view ->
            Snackbar.make(view, "Replace with youe own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.alerts -> {
                    Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show()
                    true
                }
                // возможно переделать finish() не особо подходит
                R.id.settings -> {
                    startActivity(SettingsActivity.newIntent(this@DashboardActivity,currentUserId!!))
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    companion object {
        private const val EXTRA_CURRENT_USER_ID = "current_id"
        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            return intent
        }
    }

}