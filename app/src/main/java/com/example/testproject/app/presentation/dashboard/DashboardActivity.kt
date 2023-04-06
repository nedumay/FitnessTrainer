package com.example.testproject.app.presentation.dashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.notification.NotificationActivity
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.app.presentation.settings.SettingsActivity
import com.example.testproject.databinding.ActivityDashboardBinding
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_CURRENT_USER_ID)) {
            finish()
            return
        }
        Log.d("Login account", intent.hasExtra(EXTRA_CURRENT_USER_ID).toString() )

        appBarMenu()

        binding.cardClickToStart.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, NotificationActivity::class.java))
        }
    }

    private fun appBarMenu() {

        binding.appBarLayout.setOnClickListener { view ->
            Snackbar.make(view, "Replace with youe own action",Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.alerts -> {
                    Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    startActivity(Intent(this@DashboardActivity, SettingsActivity::class.java))
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
        fun newIntent(context: Context, currentUserId: String) : Intent{
            val intent = Intent(context,DashboardActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            return intent
        }
    }

}