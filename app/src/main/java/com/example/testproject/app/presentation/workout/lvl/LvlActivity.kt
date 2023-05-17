package com.example.testproject.app.presentation.workout.lvl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.ActivityLvlBinding
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LvlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLvlBinding

    private val component by lazy {
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: LvlViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLvlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[LvlViewModel::class.java]

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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LvlActivity::class.java)
        }
    }
}