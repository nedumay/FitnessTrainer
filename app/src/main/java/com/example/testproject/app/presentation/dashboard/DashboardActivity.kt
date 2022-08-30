package com.example.testproject.app.presentation.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.app.presentation.notification.NotificationActivity
import com.example.testproject.app.presentation.settings.SettingsActivity
import com.example.testproject.databinding.ActivityDashboardBinding
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}