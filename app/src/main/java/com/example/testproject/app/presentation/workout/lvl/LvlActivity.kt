package com.example.testproject.app.presentation.workout.lvl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.example.testproject.R
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.databinding.ActivityLvlBinding
import com.google.android.material.snackbar.Snackbar

class LvlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLvlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLvlBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lvl)

        appBarMenu()
    }

    private fun appBarMenu() {

        binding.appBarLayout.setOnClickListener { view ->
            Snackbar.make(view, "Replace with you own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        binding.topAppBarWorkout.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeDashboard -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_workout, menu)
        return true
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, LvlActivity::class.java)
        }
    }
}